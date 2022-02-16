package com.rabbitmq.producer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.producer.entity.Employee;
import com.rabbitmq.producer.entity.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class HumanResourceProducer {

    private static final Logger logger = LoggerFactory.getLogger(HumanResourceProducer.class);
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper mapper;

    public HumanResourceProducer(RabbitTemplate rabbitTemplate, ObjectMapper mapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.mapper = mapper;
    }

    public void sendMessage(Employee employee) throws JsonProcessingException {
        String jsonEmployee = mapper.writeValueAsString(employee);
        logger.info("employee = {}", jsonEmployee);

        // Exchange 를 대상으로 message send
        // 해당 Exchange 에 바인딩 된 모든 Queue에 message를 전달한다.
        rabbitTemplate.convertAndSend("x.hr", "", jsonEmployee);
    }
}
