package com.lab2.consumer.jackson_mapper;

import com.lab2.entity.DummyMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

//@Service
public class DummyConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(DummyConsumer.class);

    @RabbitListener(queues = {"q.dummy"}, id = "q_dummy")
    private void listener(DummyMessage message) {
        LOG.info("dummyMessage={}", message);
    }

}
