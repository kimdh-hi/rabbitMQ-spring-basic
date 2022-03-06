package com.rabbitmq.producer.producer.echange_topic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.producer.entity.Picture;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.io.IOException;

//@Service
public class PictureTwoProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper om;

    public PictureTwoProducer(RabbitTemplate rabbitTemplate, ObjectMapper om) {
        this.rabbitTemplate = rabbitTemplate;
        this.om = om;
    }

    public void sendMessage(Picture picture) throws IOException {
        String pictureJson = om.writeValueAsString(picture);

        StringBuilder sb = new StringBuilder();
        sb.append(picture.getSource()).append(".");
        if (picture.getSize() > 4000) {
            sb.append("large");
        } else {
            sb.append("small");
        }
        sb.append(".");

        sb.append(picture.getType());

        rabbitTemplate.convertAndSend("x.picture2", sb.toString(), pictureJson);
    }
}
