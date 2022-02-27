package com.lab2;

import com.lab2.entity.test.TestEntityA;
import com.lab2.entity.test.TestEntityB;
import com.lab2.entity.test.TestEntityC;
import com.lab2.entity.test.TestEntityD;
import com.lab2.producer.review.TestProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TestRunner implements CommandLineRunner {

    @Autowired private TestProducer producer;

    @Override
    public void run(String... args) throws Exception {
        TestEntityA testEntityA = new TestEntityA(1L, "nameA", "descriptionA");
        producer.sendMessageEntityA(testEntityA);

        TestEntityB testEntityB = new TestEntityB(2L, "nameB", "descriptionB", LocalDate.now());
        producer.sendMessageEntityB(testEntityB);

        TestEntityC testEntityC = new TestEntityC(3L, "nameC", "descriptionC", LocalDate.now());
        producer.sendMessageEntityC(testEntityC);

        TestEntityD testEntityD = new TestEntityD(4L, "nameD", "descriptionD", LocalDate.now());
        producer.sendMessageEntityD(testEntityD);
    }

    //    @Autowired
//    private InvoiceProducer producer;

    /**
     * 동일한 fan-out 타입 exchange 로 routing-key 없이 서로 다른 두 개 타입의 메시지를 보낸다.
     */
//    @Override
//    public void run(String... args) throws Exception {
//        String invoiceNumber = "INV-" + ThreadLocalRandom.current().nextInt(100, 200);
//        InvoiceCreatedMessage invoiceMessage = new InvoiceCreatedMessage(152.26, LocalDate.now().minusDays(2), "USD", invoiceNumber);
//        producer.sendInvoiceCreated(invoiceMessage);
//
//        invoiceNumber = "INV-" + ThreadLocalRandom.current().nextInt(100, 200);
//        String paymentNumber = "PAY-" + ThreadLocalRandom.current().nextInt(800, 1000);
//        InvoicePaidMessage paidMessage = new InvoicePaidMessage(invoiceNumber, LocalDate.now(), paymentNumber);
//        producer.sendInvoicePaid(paidMessage);
//
//        invoiceNumber = "INV-" + ThreadLocalRandom.current().nextInt(100, 200);
//        String cancelNumber = "PAY-" + ThreadLocalRandom.current().nextInt(800, 1000);
//        InvoiceCanceledMessage cancelMessage = new InvoiceCanceledMessage(LocalDate.now(), invoiceNumber, cancelNumber, "reason");
//        producer.sendInvoiceCanceled(cancelMessage);
//    }
}
