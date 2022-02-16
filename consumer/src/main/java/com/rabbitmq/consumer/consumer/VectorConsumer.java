package com.rabbitmq.consumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.consumer.entity.Picture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

//@Service
public class VectorConsumer {

    private static final Logger logger = LoggerFactory.getLogger(VectorConsumer.class);
    private final ObjectMapper mapper;

    public VectorConsumer(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @RabbitListener(queues = "x.picture.vector")
    public void listener(String message) throws JsonProcessingException {
        Picture picture = mapper.readValue(message, Picture.class);
        logger.info("[vector] message = {}", picture);
    }
}
