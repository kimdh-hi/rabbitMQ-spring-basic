package com.rabbitmq.producer;

import com.rabbitmq.producer.producer.review.TestDto;
import com.rabbitmq.producer.producer.review.TestProducer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableScheduling
@SpringBootApplication
public class ProducerApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}

	private final TestProducer testProducer;

	public ProducerApplication(TestProducer testProducer) {
		this.testProducer = testProducer;
	}

	@Override
	public void run(String... args) throws Exception {
		for(int i=0;i<5;i++) {
			testProducer.sendMessage(new TestDto("name" + i, "keyA"));
		}
		for(int i=0;i<5;i++) {
			testProducer.sendMessage(new TestDto("name" + i, "keyB"));
		}
	}
}
