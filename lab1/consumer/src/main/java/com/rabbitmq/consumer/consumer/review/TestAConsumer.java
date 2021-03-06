package com.rabbitmq.consumer.consumer.review;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

//@Service
public class TestAConsumer {

    private static final Logger logger = LoggerFactory.getLogger(TestAConsumer.class);

    @RabbitListener(queues = {"x.Ex.a"})
    public void listener(String message) {
        logger.info("x.Ex.a message={}", message);
    }
}
