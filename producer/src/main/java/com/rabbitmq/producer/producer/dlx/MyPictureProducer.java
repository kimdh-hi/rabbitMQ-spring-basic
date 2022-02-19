package com.rabbitmq.producer.producer.dlx;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.producer.entity.Picture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MyPictureProducer {

    private final RabbitTemplate template;
    private final static Logger logger = LoggerFactory.getLogger(MyPictureProducer.class);
    private final ObjectMapper mapper;

    public MyPictureProducer(RabbitTemplate template, ObjectMapper mapper) {
        this.template = template;
        this.mapper = mapper;
    }

    public void sendMessage(Picture picture) throws JsonProcessingException {
        logger.info("message = {}", picture);
        String pictureJson = mapper.writeValueAsString(picture);

        template.convertAndSend("x.mypicture", picture.getType(), pictureJson);
    }
}
