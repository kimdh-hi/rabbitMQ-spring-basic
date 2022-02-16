package com.rabbitmq.producer;

import com.rabbitmq.producer.entity.Employee;
import com.rabbitmq.producer.entity.Member;
import com.rabbitmq.producer.entity.Picture;
import com.rabbitmq.producer.producer.HumanResourceProducer;
import com.rabbitmq.producer.producer.MemberJsonProducer;
import com.rabbitmq.producer.producer.PictureProducer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;

//@EnableScheduling
@SpringBootApplication
public class ProducerApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}

	private final PictureProducer producer;

	public ProducerApplication(PictureProducer producer) {
		this.producer = producer;
	}

	@Override
	public void run(String... args) throws Exception {
		for (int i=0;i<3;i++) {
			producer.sendMessage(new Picture("name" + i, "jpg", "source" + i, i));
		}

		for (int i=0;i<3;i++) {
			producer.sendMessage(new Picture("name" + i, "png", "source" + i, i));
		}

		for (int i=0;i<3;i++) {
			producer.sendMessage(new Picture("name" + i, "svg", "source" + i, i));
		}
	}
}
