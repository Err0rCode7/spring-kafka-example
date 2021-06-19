package com.example.kafka.consumer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MyConsumerConfig {
    private final Properties props = new Properties();

    public void put(String flag, Object value) {
        props.put(flag, value);
    }

    public Properties getProps() {
        return props;
    }
}
