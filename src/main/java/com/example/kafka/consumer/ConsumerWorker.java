package com.example.kafka.consumer;

import com.example.kafka.ConfigProperties;
import com.example.kafka.domain.Order;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.beans.factory.annotation.Value;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;


public class ConsumerWorker implements Runnable {
    private Properties prop;
    private String topic;
    private String threadName;
    private KafkaConsumer<String, Order> consumer;

    ConsumerWorker(Properties prop, String topic, int number) {
        this.prop = prop;
        this.topic = topic;
        this.threadName = "consumer-thread-" + number;
    }

    @Override
    public void run() {
        consumer = new KafkaConsumer<>(prop);
        consumer.subscribe(Arrays.asList(topic));
        try {
            while (true) {
                ConsumerRecords<String, Order> records = consumer.poll(Duration.ofSeconds(1));
                for (ConsumerRecord<String, Order> record : records) {
                    System.out.println(threadName + " >> " + record.value());
                }
                consumer.commitSync();
            }
        } catch (WakeupException e) {
            // 중간에 스레드에 문제가 발생하여 꺼지게 되면 wakeup을 트리거하여 현재 인덱스까지의 내용을 카프카에 커밋함
            System.out.println(threadName + " trigger WakeupException");
        } finally {
            consumer.commitSync();
            consumer.close();
        }
    }

    public void shutdown() {
        consumer.wakeup();
    }
}
