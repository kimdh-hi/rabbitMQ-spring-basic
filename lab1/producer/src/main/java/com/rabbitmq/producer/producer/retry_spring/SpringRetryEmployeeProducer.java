package com.rabbitmq.producer.producer.retry_spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.producer.entity.Employee;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SpringRetryEmployeeProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper om;

    public SpringRetryEmployeeProducer(RabbitTemplate rabbitTemplate, ObjectMapper om) {
        this.rabbitTemplate = rabbitTemplate;
        this.om = om;
    }

    public void sendMessage(Employee employee) throws IOException {
        String employeeJson = om.writeValueAsString(employee);

        rabbitTemplate.convertAndSend("x.employee.work", "", employeeJson);
    }
}
