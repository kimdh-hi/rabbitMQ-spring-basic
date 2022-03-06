package com.rabbitmq.consumer.consumer.exchange_topic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.consumer.entity.Picture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

//@Service
public class PictureTwoConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(PictureTwoConsumer.class);

    private final ObjectMapper om;

    public PictureTwoConsumer(ObjectMapper om) {
        this.om = om;
    }

    @RabbitListener(queues = {"q.picture.image", "q.picture.vector", "q.picture.log", "q.picture.filter"})
    public void listen(Message messageAmqp) throws IOException {
        String body = new String(messageAmqp.getBody());
        Picture picture = om.readValue(body, Picture.class);

        LOG.info("image={}, routing-key={}", picture, messageAmqp.getMessageProperties().getReceivedRoutingKey());
    }
}
