### π Postman workspace & Collection μμ±
**workspace** μμ±

![](https://images.velog.io/images/dhk22/post/25d6fce6-63b6-4bb9-ade6-9f0719cedef5/image.png)

**Collection** μμ±
![](https://images.velog.io/images/dhk22/post/2ed71c35-b94c-46da-a0e2-bc146e5612cc/image.png)

### π νκ²½λ³μ μμ±
`RabbitMQ` μλ²μ `url`κ³Ό `virtual host`μ κ²½μ° μ¬λ¬ νμ€νΈμμ λ°λ³΅μ μΌλ‘ μ¬μ©λλ―λ‘ νκ²½λ³μλ‘ μμ±ν©λλ€.

`virtual host`λ μ΅μ΄ `guest` κ³μ μ μ¬μ©νλ€λ©΄ `/` μλλ€. 
![](https://images.velog.io/images/dhk22/post/bb14f2f5-927c-4842-aa3f-7ccd47b1565e/image.png)
νΉμλ¬Έμμ κ²½μ° urlμ μ¬μ©μ url μΈμ½λ©μ μνν΄μΌ νκΈ° λλ¬Έμ `/`μ μΈμ½λ© λ κ°μ λ£μ΄μ€λλ€. (`%2f`)

![](https://images.velog.io/images/dhk22/post/1e5606f9-9540-4f42-97f1-20f54a0ed79a/image.png)

### π RabbitMQ μΈμ¦μ λ³΄ μ€μ  (μΈμ¦ν€λ μΆκ°)
`RabbitMQ`λ `basic auth` λ°©μμΌλ‘ μΈμ¦μ΄ κ°λ₯ν©λλ€.

`basic auth`λ `username`κ³Ό `password`λ‘ μΈμ¦μ μννλ κ²μ΄κ³  `RabbitMQ`κ° κΈ°λ³Έμ μΌλ‘ μ κ³΅νλ `admin` κ³μ μΈ `guest`λ₯Ό μ¬μ©ν©λλ€.

![](https://images.velog.io/images/dhk22/post/0f1b2f4a-3030-4357-951e-51332e807240/image.png)

`Collection`μ μΈμ¦κ΄λ ¨ μ λ³΄(ν€λ)λ₯Ό μΈννλ©΄ `Collection`μ΄ν λͺ¨λ  requestμμ μ¬μ© κ°λ₯ν©λλ€.



### π Exchangeμ message μ μ‘ API νΈμΆ νμ€νΈ
![](https://images.velog.io/images/dhk22/post/7102bd2e-ca20-4a58-b345-08816f6d9dbb/image.png)

κ°λ¨νκ² `fan-out` νμμ `Exchange`λ₯Ό μμ±νκ³  μ APIλ₯Ό μ΄μ©ν΄μ λ©μμ§ νλλ₯Ό μ μ‘ν©λλ€.

`fan-out` νμ `Exchange` μμ±
![](https://images.velog.io/images/dhk22/post/22be356f-8d75-4fba-8842-0a270f3b1ea9/image.png)
μμ±λ `Exchange`μ λ°μΈλ© μν¬ `Queue` νλλ₯Ό μμ±νκ³  λ°μΈλ© ν©λλ€.
![](https://images.velog.io/images/dhk22/post/75a98911-3366-4563-ba31-24e77328070b/image.png)

`Exchange`μ messageλ₯Ό λ³΄λ΄λ APIλ μλμ κ°μ΅λλ€.

```
{{url}}/api/exchanges/{{vhosts}}/:exchangeName/publish
```

`:exchangeName` λΆλΆμ `path variable` μλλ€.
messageλ₯Ό λ³΄λ΄κ³ μ νλ `Exchange`μ μ΄λ¦μ λ£μ΅λλ€.

![](https://images.velog.io/images/dhk22/post/06fc1583-bb04-480e-a36f-56e7a7a622da/image.png)

μ΄μ  APIκ° μκ΅¬νλ body λΆλΆμ JSON ννλ‘ κ΅¬μ±ν©λλ€.
![](https://images.velog.io/images/dhk22/post/63534bf2-4245-4a2f-8c30-280362da6578/image.png)
`payload` λΆλΆμ΄ messageμ λ΄μ©μ΄ λ©λλ€.

νμ€νΈνκ³ μ νλ `Exchage`μ νμμ΄ `fan-out`μ΄λ―λ‘ `routing-key`λ λΉμλ‘λλ€.

![](https://images.velog.io/images/dhk22/post/e6b65b43-3947-4d06-b9cc-800cc32f45c0/image.png)

μΌλ¨ μλ΅μ `true`λ‘ μ μμ΅λλ€.
μ€μ  `Exchange`μ λ°μΈλ© λ `Queue`λ‘ messageκ° μ λ€μ΄κ°λμ§ νμΈν©λλ€.

![](https://images.velog.io/images/dhk22/post/2172658f-d050-4c09-a077-c19bb5e8b82e/image.png)