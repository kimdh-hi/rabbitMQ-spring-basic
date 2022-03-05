`Consumer`에 스케줄링 기능을 추가해서 **은행 점검 시간** 과 비슷한 구조를 갖도록 할 것 입니다.

`00:00 ~ 00:30` 까지 은행 점검 시간으로 인해 결제가 되지 않는다고 가정하면 `00:00` 부터 쌓이는 결제 관련 message 들은 `Consumer`에 의해 처리되지 않도록 해야 합니다.

그리고 `00:30`이 되면 `Consumer`는 다시 결제 관련 `message`를 처리해야 합니다.

이와 같은 기능을 `Spring Scheduler`를 이용해서 구현할 것 입니다.

<br>

++
은행 점검과 조금 다른 점 하나는 `Consumer`가 동작을 멈춘 동안에도 message는 쌓인다는 것 입니다.

***

## 📌 Code

**RabbitMQ 의존성 추가**
```java
implementation 'org.springframework.boot:spring-boot-starter-amqp'
```

스케줄러는 spring이 제공하는 scheduler를 그대로 사용합니다.

`RabbitListenerEndpointRegistry`를 이용해서 현재 구동중인 `Consumer`를 중단하고 시작하는 것이 가능합니다.

### ✅ Consumer Code
```java
@Service
public class RabbitmqScheduler {

    private final RabbitListenerEndpointRegistry registry;
    private static final Logger LOG = LoggerFactory.getLogger(RabbitmqScheduler.class);

    public RabbitmqScheduler(RabbitListenerEndpointRegistry registry) {
        this.registry = registry;
    }

    @Scheduled(cron = "0 54 19 * * *")
    public void stopAll() {
        LOG.info("stopAll");
        registry.getListenerContainers().forEach(c -> {
                    LOG.info("stop listener container: {}", c);
                    c.stop();
                });
    }

    @Scheduled(cron = "0 55 19 * * *")
    public void startAll() {
        LOG.info("startAll");
        registry.getListenerContainers().forEach(c -> {
            LOG.info("start listener container: {}", c);
            c.start();
        });
    }
}
```
`stop()`과 `start()` 메서드로 중단, 재시작이 가능합니다.


### ✅ Test

`Producer`는 1초에 한 개씩 message를 보내게 해두고 `Consumer`가 멈추고 시작되는 것을 테스트합니다.

점검 시작시간: `19:54`
점검 종료시간: `19:55`

![](https://images.velog.io/images/dhk22/post/193b1c13-8f55-4ae8-b557-eeb0d4e5cf8c/image.png)

`19:53:xx` 까지 message를 처리하고 `19:54:xx` 부터 `Consumer`의 동작이 멈추는 것을 확인할 수 있습니다.

그리고 `19:55:xx`가 되는 순간 `Consumer`는 다시 동작합니다.

### 📌 특정 Consumer의 동작만 제어하도록 처리
위 코드의 경우 현재 구동 중인 모든 `Consumer`를 제어합니다.

특정 `Consumer`에 대해서만 제어가 가능하도록 변경해볼께요.

`Consumer`를 식별하기 위해 `Consumer`에 `id`값을 설정해줘야 합니다.
```java
@Service
public class DummyConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(DummyConsumer.class);

    @RabbitListener(queues = {"q.dummy"}, id = "q_dummy")
    private void listener(DummyMessage message) {
        LOG.info("dummyMessage={}", message);
    }

}
```
`@RabbitListner`에 `id`값을 `q_dummy`로 설정했습니다.

#### 스케줄러 코드
```java
@Service
public class RabbitmqScheduler {

    private final RabbitListenerEndpointRegistry registry;
    private static final Logger LOG = LoggerFactory.getLogger(RabbitmqScheduler.class);
    private final String containerId = "q_dummy";

    public RabbitmqScheduler(RabbitListenerEndpointRegistry registry) {
        this.registry = registry;
    }

    @Scheduled(cron = "0 40 21 * * *")
    public void stopAll() {
        LOG.info("stopAll");
        registry.getListenerContainer(containerId).stop();
    }

    @Scheduled(cron = "0 41 21 * * *")
    public void startAll() {
        LOG.info("startAll");
        registry.getListenerContainer(containerId).start();
    }
}
```

`getListenerContainers`가 아닌 `getListenerContainer`를 사용해서 특정 `id`에 대해 제어하도록 변경했습니다.

### ✅ Test
점검 시작시간: `21:40`
점검 종료시간: `21:41`
![](https://images.velog.io/images/dhk22/post/fdf9d253-1090-48eb-ab46-362c9d44394f/image.png)

설정한 시간에 맞춰 `Consumer`의 동작이 제어되는 것을 확인할 수 있습니다.

:)