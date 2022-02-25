package com.lab2.producer.multiple_message_type;

import com.lab2.entity.InvoiceCreatedMessage;
import com.lab2.entity.InvoicePaidMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class InvoiceProducer {

    private final RabbitTemplate rabbitTemplate;

    private static final String EXCHANGE = "x.invoice";

    public InvoiceProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendInvoiceCreated(InvoiceCreatedMessage message) {
        rabbitTemplate.convertAndSend(EXCHANGE, "", message);
    }

    public void sendInvoicePaid(InvoicePaidMessage message) {
        rabbitTemplate.convertAndSend(EXCHANGE, "", message);
    }
}
