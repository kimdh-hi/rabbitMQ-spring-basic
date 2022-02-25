package com.lab2;

import com.lab2.entity.InvoiceCanceledMessage;
import com.lab2.entity.InvoiceCreatedMessage;
import com.lab2.entity.InvoicePaidMessage;
import com.lab2.producer.multiple_message_type.InvoiceProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class TestRunner implements CommandLineRunner {

    @Autowired
    private InvoiceProducer producer;

    /**
     * 동일한 fan-out 타입 exchange 로 routing-key 없이 서로 다른 두 개 타입의 메시지를 보낸다.
     */
    @Override
    public void run(String... args) throws Exception {
        String invoiceNumber = "INV-" + ThreadLocalRandom.current().nextInt(100, 200);
        InvoiceCreatedMessage invoiceMessage = new InvoiceCreatedMessage(152.26, LocalDate.now().minusDays(2), "USD", invoiceNumber);
        producer.sendInvoiceCreated(invoiceMessage);

        invoiceNumber = "INV-" + ThreadLocalRandom.current().nextInt(100, 200);
        String paymentNumber = "PAY-" + ThreadLocalRandom.current().nextInt(800, 1000);
        InvoicePaidMessage paidMessage = new InvoicePaidMessage(invoiceNumber, LocalDate.now(), paymentNumber);
        producer.sendInvoicePaid(paidMessage);

        invoiceNumber = "INV-" + ThreadLocalRandom.current().nextInt(100, 200);
        String cancelNumber = "PAY-" + ThreadLocalRandom.current().nextInt(800, 1000);
        InvoiceCanceledMessage cancelMessage = new InvoiceCanceledMessage(LocalDate.now(), invoiceNumber, cancelNumber, "reason");
        producer.sendInvoiceCanceled(cancelMessage);
    }
}
