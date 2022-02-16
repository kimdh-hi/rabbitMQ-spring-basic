package com.rabbitmq.consumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.consumer.entity.Picture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

//@Service
public class ImageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(ImageConsumer.class);
    private final ObjectMapper mapper;

    public ImageConsumer(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @RabbitListener(queues = "x.picture.image")
    public void listener(String message) throws JsonProcessingException {
        Picture picture = mapper.readValue(message, Picture.class);
        logger.info("[image] message = {}", picture);
    }
}
