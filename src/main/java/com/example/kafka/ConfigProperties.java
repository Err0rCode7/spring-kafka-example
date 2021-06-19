package com.example.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("consumer")
public class ConfigProperties {
    private String ip;
    private Integer count;
    private String topic;

    public String getTopic() {
        return topic;
    }

    public String getIp() {
        return ip;
    }

    public Integer getCount() {
        return count;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
