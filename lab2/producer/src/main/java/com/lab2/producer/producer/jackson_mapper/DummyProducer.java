package com.lab2.producer.producer.jackson_mapper;

import com.lab2.producer.entity.DummyMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class DummyProducer {

    private final RabbitTemplate rabbitTemplate;

    public DummyProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(DummyMessage message) {
        rabbitTemplate.convertAndSend("x.dummy", "", message);
    }
}
