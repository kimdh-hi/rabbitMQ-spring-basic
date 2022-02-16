package com.rabbitmq.producer.producer.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class TestProducer {

    private static final Logger logger = LoggerFactory.getLogger(TestProducer.class);
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper mapper;

    public TestProducer(RabbitTemplate rabbitTemplate, ObjectMapper mapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.mapper = mapper;
    }

    public void sendMessage(TestDto dto) throws JsonProcessingException {
        logger.info("producer message = {}", dto);
        String dtoJson = mapper.writeValueAsString(dto);

        rabbitTemplate.convertAndSend("x.Ex", dto.getType(), dtoJson);
    }
}
