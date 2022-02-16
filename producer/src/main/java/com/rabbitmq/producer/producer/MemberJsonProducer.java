package com.rabbitmq.producer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.producer.entity.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MemberJsonProducer {

    private static final Logger logger = LoggerFactory.getLogger(MemberJsonProducer.class);
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper mapper;

    public MemberJsonProducer(RabbitTemplate rabbitTemplate, ObjectMapper mapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.mapper = mapper;
    }

    public void sendMessage(Member member) throws JsonProcessingException {
        String jsonMember = mapper.writeValueAsString(member);
        logger.info("member = {}", jsonMember);
        rabbitTemplate.convertAndSend("member", jsonMember);
    }
}
