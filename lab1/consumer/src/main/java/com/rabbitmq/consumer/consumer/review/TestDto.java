package com.rabbitmq.consumer.consumer.review;

public class TestDto {

    private String name;
    private String type;

    public TestDto() {
    }

    public TestDto(String name, String type) {
        this.name = name;
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TestDto{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
