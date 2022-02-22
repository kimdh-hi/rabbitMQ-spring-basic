# Consumer에서 예외발생 시 n번 재시도 후 DLX로 큐잉하기

![](https://images.velog.io/images/dhk22/post/82eee908-ab69-4a12-a598-19221c9ad5bb/image.png)

위 그림대로 `Exchange`와 `Queue`를 구성합니다.

`Employee` 객체를 `Json` 형태 메시지로 `Exchange`에 전송하고, 해당 `Exchange`에 바인딩 된 모든 `Queue`로 메시지를 중계합니다.

`Queue`에 전달된 메시지는 `Consumer`에 의해 처리되는데 이 때 여러 문제로 인해 예외가 발생할 수 있습니다.

`Consumer`에서 예외가 발생하면 기본적으로 예외가 발생한 메시지를 `Queue`에 다시 넣고 성공할 때까지 무한히 재시도 합니다.

이제 해볼 것은 예외 발생시 무한히 재시도 되지 않고 n번 정도 재시도 한 결과 계속해서 예외가 발생한다면 해당 메시지를 `DLX (Dead-Letter-Exchange`로 보내는 것 입니다.

***

위 구성에서 `Producer`로부터 메시지를 받는 `Exchange`는 `fan-out` 타입입니다. `DLX`의 경우 각 `Queue`마다 다른 큐에 관리하고자 `direct` 타입으로 해주었습니다.

`Queue` 생성시 `Dead letter exchange`와 `Dead letter routing key`를 설정해주어 `DLX`와 매핑시켜주면 됩니다.

***

## 📌 Spring & RabbitMQ wow

Spring을 사용하면 재시도와 같은 로직을 Java 코드 없이 설정파일로 가능합니다.

재시도에 대한 설정은 `Consumer` 에만 필요하므로 `Consumer`에만 설정합니다.

```yaml
spring:
  main:
    banner-mode: OFF
  rabbitmq:
    addresses: localhost:5672
    username: guest
    password: guest
    listener:
      simple:
        acknowledge-mode: auto # manual X auto O
        retry:
          enabled: true # 재시도
          initial-interval: 3s # 최초 메시지 처리 실패 후 재시도까지의 인터벌
          max-interval: 10s # 최대 재시도 인터벌
          max-attempts: 2 # 최대 재시도 횟수
          multiplier: 2 # 이전 interval * multiplier = 다음 interval
```

## 📌 테스트
```java
@SpringBootApplication
public class ProducerApplication implements  CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}

	private final SpringRetryEmployeeProducer producer;

	public ProducerApplication(SpringRetryEmployeeProducer producer) {
		this.producer = producer;
	}

	@Override
	public void run(String... args) throws Exception {
		for (int i=0;i<5;i++){
			Employee employee;
			if (i%2==0) {
				employee = new Employee((long)i, "", LocalDate.now());
			} else {
				employee = new Employee((long)i, "name" + i, LocalDate.now());
			}
			producer.sendMessage(employee);
		}
	}
}    
```
5개 메시지를 발행하는데 짝수번째 `message`의 `name`을 빈 문자열로 전달하고 빈 문자열의 경우 `consumer`에서 예외가 발생하도록 합니다.

### ✅ Consumer code
```java
@Service
public class SpringRetryEmployeeConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(SpringRetryEmployeeConsumer.class);
    private final ObjectMapper om;

    public SpringRetryEmployeeConsumer(ObjectMapper om) {
        this.om = om;
    }

    @RabbitListener(queues = {"q.employee.development.work"})
    public void listenerDevelopment(String message) throws IOException {
        Employee employee = om.readValue(message, Employee.class);
        validateEmployee(employee);

        LOG.info("development employee: {}", employee);
    }

    @RabbitListener(queues = {"q.employee.marketing.work"})
    public void listerVector(String message) throws IOException {
        Employee employee = om.readValue(message, Employee.class);
        validateEmployee(employee);

        LOG.info("marketing employee: {}", employee);
    }

    // name이 empty인 경우 예외 발생
    private void validateEmployee(Employee employee) {
        if (employee.getName().isEmpty()) {
            throw new IllegalArgumentException("employee name cannot be empty");
        }
    }
}
```

이제 두 큐 (`marketing`, `development`)는 5개 메시지를 받아 처리하게 되는데 짝수번째 메시지에서 예외가 터지게 됩니다.

예외가 터지면 2번까지 재시도 후 `DLX`로 `routing-key`값과 함께 메시지를 전달하면서 해당 메시지의 처리를 끝냅니다.

`Consumer`와 `Producer`를 실행했을 때 5개 메시지 중 1,3 번째 메시지는 정상적으로 처리되고 0,2,4번째 메시지는 예외가 터져 두 번의 재시도를 거치고 `DLX`로 전달되는 것을 기대하고 실행합니다.


### ✅ 테스트 결과
![](https://images.velog.io/images/dhk22/post/ff63b3d1-cebc-4453-8e8e-a9bdd2e4777f/image.png)

`fan-out` 타입 `Exchange`로 전달된 메시지를 `direct` 타입 `DLX`로 전달한 테스트 결과입니다. :)
