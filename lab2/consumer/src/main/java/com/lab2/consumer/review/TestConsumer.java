package com.lab2.consumer.review;

import com.lab2.entity.test.TestEntityA;
import com.lab2.entity.test.TestEntityB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@RabbitListener(queues = {"q.test"})
@Service
public class TestConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(TestConsumer.class);

    @RabbitHandler
    public void listenerA(TestEntityA testEntityA) {
        LOG.info("TestA = {}", testEntityA);
    }

    @RabbitHandler
    public void listenerB(TestEntityB testEntityB) {
        LOG.info("TestB = {}", testEntityB);
    }
}
