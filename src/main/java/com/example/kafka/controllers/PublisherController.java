package com.example.kafka.controllers;

import com.example.kafka.ConfigProperties;
import com.example.kafka.config.KafkaConfig;
import com.example.kafka.consumer.ConsumerWithMultiThread;
import com.example.kafka.consumer.MyConsumerConfig;
import com.example.kafka.domain.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequestMapping("/kafka")
@RestController
public class PublisherController {
    private final KafkaTemplate<String, Order> kafkaTemplate;

    public PublisherController(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/publish")
    public String publish() {
        Order order = new Order.Builder()
                        .id(1L)
                        .menu("kafka_test")
                        .userName("sw")
                        .build();
        kafkaTemplate.send("test", order);
        return "test ok";
    }
}
