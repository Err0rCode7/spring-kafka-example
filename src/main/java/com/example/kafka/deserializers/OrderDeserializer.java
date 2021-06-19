package com.example.kafka.deserializers;

import com.example.kafka.domain.Order;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

public class OrderDeserializer implements Deserializer {
    @Override
    public Object deserialize(String topic, byte[] data) {
        ObjectMapper mapper = new ObjectMapper();
        Order order = null;
        try {
            order = mapper.readValue(data, Order.class);
        } catch (JsonParseException e) {
            // Order 객체가 아닌 다른 객체가 들어옴
            System.out.println("JsonParseException = " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }
}
