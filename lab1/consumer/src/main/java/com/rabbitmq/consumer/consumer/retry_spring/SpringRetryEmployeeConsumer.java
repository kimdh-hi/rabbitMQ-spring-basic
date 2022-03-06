package com.rabbitmq.consumer.consumer.retry_spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.consumer.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

//@Service
public class SpringRetryEmployeeConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(SpringRetryEmployeeConsumer.class);
    private final ObjectMapper om;

    public SpringRetryEmployeeConsumer(ObjectMapper om) {
        this.om = om;
    }

    @RabbitListener(queues = {"q.employee.development.work"})
    public void listenerDevelopment(String message) throws IOException {
        Employee employee = om.readValue(message, Employee.class);
        validateEmployee(employee);

        LOG.info("development employee: {}", employee);
    }

    @RabbitListener(queues = {"q.employee.marketing.work"})
    public void listenerMarketing(String message) throws IOException {
        Employee employee = om.readValue(message, Employee.class);
        validateEmployee(employee);

        LOG.info("marketing employee: {}", employee);
    }

    private void validateEmployee(Employee employee) {
        if (employee.getName().isEmpty()) {
            throw new IllegalArgumentException("employee name cannot be empty");
        }
    }
}
