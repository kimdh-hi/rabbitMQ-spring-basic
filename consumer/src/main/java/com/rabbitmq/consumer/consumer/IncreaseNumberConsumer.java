package com.rabbitmq.consumer.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class IncreaseNumberConsumer {

    private static final Logger logger = LoggerFactory.getLogger(IncreaseNumberConsumer.class);

    @RabbitListener(queues = {"increaseNumber"})
    public void listen(String message) {
        logger.info("message = {}", message);
    }
}