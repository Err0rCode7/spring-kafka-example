package com.example.kafka;

import com.example.kafka.consumer.ConsumerWithMultiThread;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class KafkaApplication {

	private final ConsumerWithMultiThread consumerWithMultiThread;

	public KafkaApplication(ConsumerWithMultiThread consumerWithMultiThread) {
		this.consumerWithMultiThread = consumerWithMultiThread;
	}

	public static void main(String[] args) {
		SpringApplication.run(KafkaApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void initKafkaConsumers() {
		ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.execute(consumerWithMultiThread);
	}
}
