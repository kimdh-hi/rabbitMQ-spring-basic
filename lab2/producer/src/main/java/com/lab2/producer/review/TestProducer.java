package com.lab2.producer.review;

import com.lab2.entity.test.TestEntityA;
import com.lab2.entity.test.TestEntityB;
import com.lab2.entity.test.TestEntityC;
import com.lab2.entity.test.TestEntityD;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

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
