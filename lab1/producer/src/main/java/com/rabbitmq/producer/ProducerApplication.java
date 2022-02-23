package com.rabbitmq.producer;

import com.rabbitmq.producer.entity.Employee;
import com.rabbitmq.producer.producer.retry_spring.SpringRetryEmployeeProducer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;

@EnableScheduling
@SpringBootApplication
public class ProducerApplication implements  CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}

	private final SpringRetryEmployeeProducer producer;

	public ProducerApplication(SpringRetryEmployeeProducer producer) {
		this.producer = producer;
	}

	@Override
	public void run(String... args) throws Exception {
		for (int i=0;i<5;i++){
			Employee employee;
			if (i%2==0) {
				employee = new Employee((long)i, "", LocalDate.now());
			} else {
				employee = new Employee((long)i, "name" + i, LocalDate.now());
			}
			producer.sendMessage(employee);
		}
	}

//		private final SpringRetryPictureProducer producer;
//
//	public ProducerApplication(SpringRetryPictureProducer producer) {
//		this.producer = producer;
//	}
//
//	@Override
//	public void run(String... args) throws Exception {
//		List<String> SOURCES = List.of("web", "mobile");
//		List<String> TYPES = List.of("png", "jpg", "svg");
//
//		for (int i=0;i<5;i++){
//			Picture picture = new Picture();
//			picture.setName("p" + i);
//			picture.setSize(ThreadLocalRandom.current().nextLong(9001, 10001));
//			picture.setSource(SOURCES.get(i%SOURCES.size()));
//			picture.setType(TYPES.get(i%TYPES.size()));
//
//			producer.sendMessage(picture);
//		}
//	}
}
