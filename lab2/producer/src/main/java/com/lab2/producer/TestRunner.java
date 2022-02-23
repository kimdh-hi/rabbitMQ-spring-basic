package com.lab2.producer;

import ch.qos.logback.core.util.TimeUtil;
import com.lab2.producer.entity.DummyMessage;
import com.lab2.producer.producer.jackson_mapper.DummyProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class TestRunner implements CommandLineRunner {

    @Autowired
    private DummyProducer producer;

    @Override
    public void run(String... args) throws Exception {

        for (int i=0;i<10_000;i++) {
            DummyMessage dummyMessage = new DummyMessage("content" + i, i);
            TimeUnit.SECONDS.sleep(1);
            producer.sendMessage(dummyMessage);
        }
    }
}
