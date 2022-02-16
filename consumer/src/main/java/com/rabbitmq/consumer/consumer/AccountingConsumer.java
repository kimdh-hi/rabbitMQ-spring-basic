package com.rabbitmq.consumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.consumer.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class AccountingConsumer {

    private static final Logger logger = LoggerFactory.getLogger(AccountingConsumer.class);
    private final ObjectMapper mapper;

    public AccountingConsumer(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @RabbitListener(queues = {"x.hr.accounting"})
    public void listen(String message) throws JsonProcessingException {
        Employee employee = mapper.readValue(message, Employee.class);

        logger.info("[queue: {}] message = {}", "accounting", employee);
    }
}
