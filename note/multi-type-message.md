# Multi Type Message

한 개 `Queue`에 두 개 이상 타입의 `Message`가 들어올 때의 처리입니다.

한 개 `Exchange`에 한 개 `Queue`가 바인딩 된 상태입니다.
![](https://images.velog.io/images/dhk22/post/9fa46b8a-691d-41aa-9a89-87a3b9ea6a72/image.png)

이 때 `x.test`로 두 개 타입의 메시지가 올 수 있습니다.
두 타입은 객체이고 아래와 같습니다.
![](https://images.velog.io/images/dhk22/post/310555eb-bce1-4db7-a4dc-7128e4a52eb0/image.png)

요구사항은 아래와 같습니다.
- 메시지의 타입에 따라 다른 로직을 수행해야 한다.

***

## 📌 Producer

`x.test` Exchange로 두 개 서로 다른 타입의 객체를 보내는 Producer 입니다.

```java
@Service
public class TestProducer {

    private final RabbitTemplate rabbitTemplate;

    public TestProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessageEntityA(TestEntityA message) {
        rabbitTemplate.convertAndSend("x.test", "", message);
    }

    public void sendMessageEntityB(TestEntityB message) {
        rabbitTemplate.convertAndSend("x.test", "", message);
    }
}
```
위 Producer를 통해 두 개 message를 발행합니다.
```java
@Component
public class TestRunner implements CommandLineRunner {

    @Autowired private TestProducer producer;

    @Override
    public void run(String... args) throws Exception {
        TestEntityA testEntityA = new TestEntityA(1L, "nameA", "descriptionA");
        producer.sendMessageEntityA(testEntityA);

        TestEntityB testEntityB = new TestEntityB(2L, "nameB", "descriptionB", LocalDate.now());
        producer.sendMessageEntityB(testEntityB);
    }
}    
```

한 개 Exchange로 서로 다른 두 개 타입의 메시지가 전달되었습니다.

전달된 결과는 아래와 같습니다.
![](https://images.velog.io/images/dhk22/post/aca67473-d9cc-420e-8f22-541d799ac978/image.png)

`Consumer` 측은 `headers`의 `__TypeId__`의 경로와 동일한 경로에 해당 클래스가 존재해야 합니다.

***

## 📌 Consumer
이전과 다르게 클래스 레벨에 `@RabitListener`를 지정합니다.
```java
@RabbitListener(queues = {"q.test"})
@Service
public class TestConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(TestConsumer.class);

    @RabbitHandler
    public void listenerA(TestEntityA testEntityA) {
        LOG.info("TestA = {}", testEntityA);
    }

    @RabbitHandler
    public void listenerB(TestEntityB testEntityB) {
        LOG.info("TestB = {}", testEntityB);
    }
}
```
`@RabbitHandler`를 이용해서 메시지의 타입마다 다르게 매핑되도록 설정합니다.

`TestEntityA`타입 메시지에 대해서는 `listenerA`메서드와 매핑하고,
`TestEntityB`타입 메시지에 대해서는 `listenerB`메서드를 매핑합니다.

![](https://images.velog.io/images/dhk22/post/b291920b-4991-4d69-9e73-dccbd584bf21/image.png)
`Consumer`를 실행시킨 결과 `q.test`에 큐잉된 메시지를 타입에 따라 다른 메서드를 호출하는 것을 확인할 수 있습니다.

***

## 📌 isDefault

`TestEntityA`와 `TestEntityB` 외에 두 개 타입이 더 `x.test`로 전달될 수 있다고 했을 때 `TestEntityA`와 `TestEntityB` 두 타입 외에 대한 처리를 일괄적으로 할 수 있습니다.

일단 두 개 엔티티를 추가로 정의합니다.
![](https://images.velog.io/images/dhk22/post/df6d5862-99d9-467e-ba7a-1e629fb7500a/image.png)

그리고 `Producer`는 총 4개 메시지를 모두 다른 타입으로 발행합니다.
```java
@Service
public class TestProducer {

    private final RabbitTemplate rabbitTemplate;

    public TestProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessageEntityA(TestEntityA message) {
        rabbitTemplate.convertAndSend("x.test", "", message);
    }

    public void sendMessageEntityB(TestEntityB message) {
        rabbitTemplate.convertAndSend("x.test", "", message);
    }

    public void sendMessageEntityC(TestEntityC message) {
        rabbitTemplate.convertAndSend("x.test", "", message);
    }

    public void sendMessageEntityD(TestEntityD message) {
        rabbitTemplate.convertAndSend("x.test", "", message);
    }
}
```

```java
@Component
public class TestRunner implements CommandLineRunner {

    @Autowired private TestProducer producer;

    @Override
    public void run(String... args) throws Exception {
        TestEntityA testEntityA = new TestEntityA(1L, "nameA", "descriptionA");
        producer.sendMessageEntityA(testEntityA);

        TestEntityB testEntityB = new TestEntityB(2L, "nameB", "descriptionB", LocalDate.now());
        producer.sendMessageEntityB(testEntityB);

        TestEntityC testEntityC = new TestEntityC(3L, "nameC", "descriptionC", LocalDate.now());
        producer.sendMessageEntityC(testEntityC);

        TestEntityD testEntityD = new TestEntityD(4L, "nameD", "descriptionD", LocalDate.now());
        producer.sendMessageEntityD(testEntityD);
    }
}    
```

<br>

`Consumer` 측에서 메시지를 처리할 때 `@RabbitHandler`의 `isDefault` 속성을 사용할 수 있습니다.

`@RabbitHandler`에 의해 매핑되지 않는 타입에 대한 처리를 지원합니다.
매핑되지 않는 타입일지라도 `Consumer` 측에서 해당 타입의 클래스를 `__TypeId__`의 경로에 갖고 있어야 합니다.


```java
@RabbitListener(queues = {"q.test"})
@Service
public class TestConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(TestConsumer.class);

    @RabbitHandler
    public void listenerA(TestEntityA testEntityA) {
        LOG.info("TestA = {}", testEntityA);
    }

    @RabbitHandler
    public void listenerB(TestEntityB testEntityB) {
        LOG.info("TestB = {}", testEntityB);
    }

    @RabbitHandler(isDefault = true)
    public void listenerDefault(Object obj) {
        LOG.info("Test Default = {}", obj);
    }
}
```

`TestEntityA`와 `TestEntityB` 외 타입은 `Object` 타입으로 매핑되도록 했습니다.

결과는 예상한 대로 아래와 같습니다.

![](https://images.velog.io/images/dhk22/post/7f8349b2-7095-44e5-a0cf-b8e07b576bdf/image.png)

