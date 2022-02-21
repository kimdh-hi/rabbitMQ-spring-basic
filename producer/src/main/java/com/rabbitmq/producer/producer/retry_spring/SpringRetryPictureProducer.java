package com.rabbitmq.producer.producer.retry_spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.producer.entity.Picture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

//@Service
public class SpringRetryPictureProducer {

    private static final Logger LOG = LoggerFactory.getLogger(SpringRetryPictureProducer.class);
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper om;

    public SpringRetryPictureProducer(RabbitTemplate rabbitTemplate, ObjectMapper om) {
        this.rabbitTemplate = rabbitTemplate;
        this.om = om;
    }

    public void sendMessage(Picture picture) throws IOException {
        String jsonPicture = om.writeValueAsString(picture);
        rabbitTemplate.convertAndSend("x.spring.work", picture.getType(), jsonPicture);
    }

}
