### 📌 Postman workspace & Collection 생성
**workspace** 생성

![](https://images.velog.io/images/dhk22/post/25d6fce6-63b6-4bb9-ade6-9f0719cedef5/image.png)

**Collection** 생성
![](https://images.velog.io/images/dhk22/post/2ed71c35-b94c-46da-a0e2-bc146e5612cc/image.png)

### 📌 환경변수 생성
`RabbitMQ` 서버의 `url`과 `virtual host`의 경우 여러 테스트에서 반복적으로 사용되므로 환경변수로 생성합니다.

`virtual host`는 최초 `guest` 계정을 사용한다면 `/` 입니다. 
![](https://images.velog.io/images/dhk22/post/bb14f2f5-927c-4842-aa3f-7ccd47b1565e/image.png)
특수문자의 경우 url에 사용시 url 인코딩을 수행해야 하기 때문에 `/`의 인코딩 된 값을 넣어줍니다. (`%2f`)

![](https://images.velog.io/images/dhk22/post/1e5606f9-9540-4f42-97f1-20f54a0ed79a/image.png)

### 📌 RabbitMQ 인증정보 설정 (인증헤더 추가)
`RabbitMQ`는 `basic auth` 방식으로 인증이 가능합니다.

`basic auth`는 `username`과 `password`로 인증을 수행하는 것이고 `RabbitMQ`가 기본적으로 제공하는 `admin` 계정인 `guest`를 사용합니다.

![](https://images.velog.io/images/dhk22/post/0f1b2f4a-3030-4357-951e-51332e807240/image.png)

`Collection`에 인증관련 정보(헤더)를 세팅하면 `Collection`이하 모든 request에서 사용 가능합니다.



### 📌 Exchange에 message 전송 API 호출 테스트
![](https://images.velog.io/images/dhk22/post/7102bd2e-ca20-4a58-b345-08816f6d9dbb/image.png)

간단하게 `fan-out` 타입의 `Exchange`를 생성하고 위 API를 이용해서 메시지 하나를 전송합니다.

`fan-out` 타입 `Exchange` 생성
![](https://images.velog.io/images/dhk22/post/22be356f-8d75-4fba-8842-0a270f3b1ea9/image.png)
생성된 `Exchange`에 바인딩 시킬 `Queue` 하나를 생성하고 바인딩 합니다.
![](https://images.velog.io/images/dhk22/post/75a98911-3366-4563-ba31-24e77328070b/image.png)

`Exchange`에 message를 보내는 API는 아래와 같습니다.

```
{{url}}/api/exchanges/{{vhosts}}/:exchangeName/publish
```

`:exchangeName` 부분은 `path variable` 입니다.
message를 보내고자 하는 `Exchange`의 이름을 넣습니다.

![](https://images.velog.io/images/dhk22/post/06fc1583-bb04-480e-a36f-56e7a7a622da/image.png)

이제 API가 요구하는 body 부분을 JSON 형태로 구성합니다.
![](https://images.velog.io/images/dhk22/post/63534bf2-4245-4a2f-8c30-280362da6578/image.png)
`payload` 부분이 message의 내용이 됩니다.

테스트하고자 하는 `Exchage`의 타입이 `fan-out`이므로 `routing-key`는 비워둡니다.

![](https://images.velog.io/images/dhk22/post/e6b65b43-3947-4d06-b9cc-800cc32f45c0/image.png)

일단 응답은 `true`로 잘 왔습니다.
실제 `Exchange`에 바인딩 된 `Queue`로 message가 잘 들어갔는지 확인합니다.

![](https://images.velog.io/images/dhk22/post/2172658f-d050-4c09-a077-c19bb5e8b82e/image.png)