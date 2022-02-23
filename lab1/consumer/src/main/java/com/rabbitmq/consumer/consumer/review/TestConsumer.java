package com.rabbitmq.consumer.consumer.review;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

//@Service
public class TestConsumer {

    private final ObjectMapper om;
    private static final Logger LOG = LoggerFactory.getLogger(TestConsumer.class);

    public TestConsumer(ObjectMapper om) {
        this.om = om;
    }

    @RabbitListener(queues = {"q.test"})
    public void listener(String message) throws JsonProcessingException {
        TestDto testDto = om.readValue(message, TestDto.class);
        LOG.info("q.test message = {}", testDto);
    }
}
