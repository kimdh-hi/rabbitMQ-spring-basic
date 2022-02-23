package com.rabbitmq.consumer.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

//@Service
public class IncreaseNumberMultipleConsumer {

    private static final Logger logger = LoggerFactory.getLogger(IncreaseNumberMultipleConsumer.class);

    @RabbitListener(queues = {"increaseNumber"}, concurrency = "3") // 3개 consumer가 동작
    public void listen(String message) throws InterruptedException {
        // 큐의 메시지를 consume 하는데 비즈니스 로직, DB접근 등으로 1~2초 정도가 소요된다고 가정
        TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextLong(1000, 2000));

        logger.info("message = {} , Thread = {}", message, Thread.currentThread().getName());
    }
}