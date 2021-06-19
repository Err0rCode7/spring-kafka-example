package com.example.kafka.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.apache.kafka.common.serialization.Serializer;
import org.aspectj.weaver.ast.Or;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Entity
@ToString
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    private String menu;

    @Column(name = "user_name")
    private String userName;

    public Order() {
    }

    public static class Builder {
        private Long id;
        private String menu;
        private String userName;
        public Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder menu(String menu) {
            this.menu = menu;
            return this;
        }

        public Builder userName(String name) {
            this.userName = name;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }

    public Order(Builder builder) {
        this.id = builder.id;
        this.menu = builder.menu;
        this.userName = builder.userName;
    }
}
