package com.rabbitmq.consumer.consumer.review;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

//@Service
public class TestBConsumer {

    private static final Logger logger = LoggerFactory.getLogger(TestBConsumer.class);

    @RabbitListener(queues = {"x.Ex.b"})
    public void listener(String message) {
        logger.info("x.Ex.b message={}", message);
    }
}
