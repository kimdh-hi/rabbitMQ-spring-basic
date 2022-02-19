package com.rabbitmq.producer;

import com.rabbitmq.producer.entity.Picture;
import com.rabbitmq.producer.producer.dlx.MyPictureProducer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

//@EnableScheduling
@SpringBootApplication
public class ProducerApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}

	private final MyPictureProducer producer;

	public ProducerApplication(MyPictureProducer producer) {
		this.producer = producer;
	}

	private final List<String> sources = List.of("mobile", "web");
	private final List<String> types = List.of("jpg", "png", "svg");

	@Override
	public void run(String... args) throws Exception {
		for (int i=0;i<10;i++) {
			Picture picture = new Picture(
					"p"+i, types.get(i%types.size()), sources.get(i%sources.size()), ThreadLocalRandom.current().nextLong(9000, 10000));

			producer.sendMessage(picture);
		}
	}
}
