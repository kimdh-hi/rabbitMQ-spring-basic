package com.rabbitmq.consumer.consumer.exchnage_direct;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.consumer.entity.Picture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

//@Service
public class PictureImageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(PictureImageConsumer.class);
    private final ObjectMapper mapper;

    public PictureImageConsumer(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @RabbitListener(queues = "x.picture.image")
    public void listener(String message) throws JsonProcessingException {
        Picture picture = mapper.readValue(message, Picture.class);
        logger.info("[image] message = {}", picture);
    }
}
