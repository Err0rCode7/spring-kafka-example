package com.example.kafka.controllers;

import com.example.kafka.domain.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/")
@RestController
public class Publisher {

    private final KafkaTemplate<String, Order> kafkaTemplate;

    public Publisher(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("ctr_test")
    public String ctrTest() {
        return "That's ok";
    }

    @GetMapping("kafka_test")
    public String test() {
        Order order = new Order.Builder()
                        .id(1L)
                        .menu("kafka_test")
                        .userName("sw")
                        .build();
        kafkaTemplate.send("test", order);
        return "testok";
    }
}
