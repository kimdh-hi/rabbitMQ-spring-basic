package com.rabbitmq.consumer.consumer.dlx;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.consumer.entity.Picture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MyPictureImageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(MyPictureImageConsumer.class);
    private final ObjectMapper mapper;

    public MyPictureImageConsumer(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @RabbitListener(queues = "q.mypicture.image")
    public void listener(String message) throws IOException {
        Picture picture = mapper.readValue(message, Picture.class);

        // 일반적인 queue에서 메시지에 대한 처리 중 예외가 발생했다면 다시 Queue에 넣고 재실행하는 과정이 무한하게 반복된다.
        if (picture.getSize() >= 9000) {
            // q.mypicture.image에 dead letter exchange(DLX)를 설정하고 AmqpRejectAndDontRequeueException를 발생시키면
            // 해당 메시지는 무한하게 requeue 되지 않고 dlx로 보내진다.
            throw new AmqpRejectAndDontRequeueException("too large size image. picture=" + picture);
        }

        logger.info("[image] message = {}", picture);
    }
}
