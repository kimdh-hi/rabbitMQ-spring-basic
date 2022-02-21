package com.rabbitmq.consumer.consumer.retry_spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.consumer.entity.Picture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SpringRetryPictureConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(SpringRetryPictureConsumer.class);
    private final ObjectMapper om;

    public SpringRetryPictureConsumer(ObjectMapper om) {
        this.om = om;
    }

    @RabbitListener(queues = {"q.spring.image.work"})
    public void listerImage(String message) throws IOException {
        Picture picture = om.readValue(message, Picture.class);
        if (picture.getSize() > 9000) {
            throw new IllegalArgumentException("image size is too large. image name: " + picture.getName());
        }
        LOG.info("consume picture. name: {}", picture.getName());
    }

    @RabbitListener(queues = {"q.spring.vector.work"})
    public void listerVector(String message) throws IOException {
        Picture picture = om.readValue(message, Picture.class);
        if (picture.getSize() > 9000) {
            throw new IllegalArgumentException("image size is too large. image name: " + picture.getName());
        }
        LOG.info("consume picture. name: {}", picture.getName());
    }
}
