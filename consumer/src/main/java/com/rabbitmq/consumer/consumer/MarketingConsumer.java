package com.rabbitmq.consumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.consumer.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

//@Service
public class MarketingConsumer {

    private static final Logger logger = LoggerFactory.getLogger(MarketingConsumer.class);
    private final ObjectMapper mapper;

    public MarketingConsumer(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @RabbitListener(queues = {"x.hr.marketing"})
    public void listen(String message) throws JsonProcessingException {
        Employee employee = mapper.readValue(message, Employee.class);
        logger.info("[queue: {}] message = {}", "marketing", employee);
    }
}
