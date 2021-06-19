package com.example.kafka;

import com.example.kafka.consumer.MyConsumerConfig;
import com.example.kafka.deserializers.OrderDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class KafkaConfig {

    @Bean
    public MyConsumerConfig myConsumerConfig() {
        MyConsumerConfig config = new MyConsumerConfig();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "18.117.100.143:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "test_group");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, OrderDeserializer.class);
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        return config;
    }
}
