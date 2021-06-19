package com.example.kafka.consumer;

import com.example.kafka.ConfigProperties;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ConsumerWithMultiThread implements Runnable {
    private final String TOPIC_NAME;
    private final String BOOTSTRAP_SERVERS;
    private final int CONSUMER_COUNT;
    private final ConfigProperties configProperties;
    private final MyConsumerConfig myConsumerConfig;
    private static List<ConsumerWorker> workerThreads = new ArrayList<>();

    @Override
    public void run() {
        Runtime.getRuntime().addShutdownHook(new ShutdownThread());
        consume();
    }

    public ConsumerWithMultiThread(ConfigProperties configProperties, MyConsumerConfig myConsumerConfig) {
        this.configProperties = configProperties;
        this.TOPIC_NAME = configProperties.getTopic();
        this.BOOTSTRAP_SERVERS = configProperties.getIp();
        this.CONSUMER_COUNT = configProperties.getCount();
        this.myConsumerConfig = myConsumerConfig;
    }

    public void consume() {

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < CONSUMER_COUNT; i++) {
            ConsumerWorker worker = new ConsumerWorker(myConsumerConfig.getProps(), TOPIC_NAME, i);
            workerThreads.add(worker);
            executorService.execute(worker);
        }
    }

    static class ShutdownThread extends Thread {
        public void run() {
            workerThreads.forEach(ConsumerWorker::shutdown);
            System.out.println("Bye");
        }
    }
}
