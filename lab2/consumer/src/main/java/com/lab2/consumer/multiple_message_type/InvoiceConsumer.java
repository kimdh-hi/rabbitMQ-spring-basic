package com.lab2.consumer.multiple_message_type;

import com.lab2.entity.InvoiceCreatedMessage;
import com.lab2.entity.InvoicePaidMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@RabbitListener(queues = {"q.invoice"})
//@Service
public class InvoiceConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(InvoiceConsumer.class);

    @RabbitHandler
    public void handleInvoiceCreated(InvoiceCreatedMessage message) {
        LOG.info("invoiceCreated message={}", message);
    }

    @RabbitHandler
    public void handleInvoicePaid(InvoicePaidMessage message) {
        LOG.info("invoicePaid message={}", message);
    }

    @RabbitHandler(isDefault = true)
    public void handleDefaultTypeMessage(Object message) {
        LOG.info("invoice default-handler={}", message);
    }
}
