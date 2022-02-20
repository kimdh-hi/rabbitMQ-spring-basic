package com.rabbitmq.producer.producer.retry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.producer.entity.Picture;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

//@Service
public class RetryPictureProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper mapper;;

    public RetryPictureProducer(RabbitTemplate rabbitTemplate, ObjectMapper mapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.mapper = mapper;
    }

    public void sendMessage(Picture picture) throws IOException {
        String pictureJson = mapper.writeValueAsString(picture);
        rabbitTemplate.convertAndSend("x.guideline.work", picture.getType(), pictureJson);
    }
}
