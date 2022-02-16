package com.rabbitmq.consumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.consumer.entity.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

//@Service
public class MemberJsonConsumer {

    private static final Logger logger = LoggerFactory.getLogger(MemberJsonConsumer.class);
    private final ObjectMapper mapper;

    public MemberJsonConsumer(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @RabbitListener(queues = {"member"})
    public void listen(String message) throws JsonProcessingException {
        Member member = mapper.readValue(message, Member.class);
        logger.info("message = {}", member);

    }
}
