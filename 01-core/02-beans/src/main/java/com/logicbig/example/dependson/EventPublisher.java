package com.logicbig.example.dependson;

public class EventPublisher {

    public void initialize() {
        System.out.println("EventPublisherBean initializing");
        EventManager.getInstance().publish("event published from EventPublisher");
    }
}