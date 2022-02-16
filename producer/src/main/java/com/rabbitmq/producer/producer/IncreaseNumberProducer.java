package com.rabbitmq.producer.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class IncreaseNumberProducer {
    private static final Logger logger = LoggerFactory.getLogger(IncreaseNumberProducer.class);
    private AtomicLong number = new AtomicLong(0L);

    private final RabbitTemplate rabbitTemplate;

    public IncreaseNumberProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedRate = 1000)
    public void increaseNumber() {
        long number = this.number.incrementAndGet();
        rabbitTemplate.convertAndSend("increaseNumber", number);
        logger.info("increaseNumber = {}", number);
    }
}
