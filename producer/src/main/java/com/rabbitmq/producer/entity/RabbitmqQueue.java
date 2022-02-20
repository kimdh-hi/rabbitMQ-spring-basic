package com.rabbitmq.producer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RabbitmqQueue {

    private long messages;
    private String name;

    public boolean isDirty() {
        return messages > 0;
    }

    public long getMessages() {
        return messages;
    }

    public void setMessages(long messages) {
        this.messages = messages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RabbitmqQueue{" +
                "messages=" + messages +
                ", name='" + name + '\'' +
                '}';
    }
}
