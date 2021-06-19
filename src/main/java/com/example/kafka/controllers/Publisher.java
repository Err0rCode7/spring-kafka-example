package com.example.kafka.controllers;

import com.example.kafka.ConfigProperties;
import com.example.kafka.KafkaConfig;
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
public class Publisher {
    private final ConfigProperties configProperties;
    private final MyConsumerConfig myConsumerConfig;
    private final KafkaTemplate<String, Order> kafkaTemplate;
    private final ConsumerWithMultiThread consumerWithMultiThread;
    private Boolean existConsumer;

    public Publisher(ConfigProperties configProperties, KafkaTemplate<String, Order> kafkaTemplate, KafkaConfig kafkaConfig, MyConsumerConfig myConsumerConfig, ConsumerWithMultiThread consumerWithMultiThread) {
        this.configProperties = configProperties;
        this.kafkaTemplate = kafkaTemplate;
        this.myConsumerConfig = myConsumerConfig;
        this.consumerWithMultiThread = consumerWithMultiThread;
        this.existConsumer = false;
    }

    @GetMapping("ctr_test")
    public String ctrTest() {
        return "That's ok";
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

    @GetMapping("/subscribe")
    public String subscribe() {
        synchronized(existConsumer) {
            if (existConsumer)
                return "already exist three consumers";
            existConsumer = true;
        }

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(consumerWithMultiThread);

        return "test ok";
    }
}
