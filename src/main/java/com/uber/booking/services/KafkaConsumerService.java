package com.uber.booking.services;

import org.springframework.kafka.annotation.KafkaListener;

public class KafkaConsumerService {

    @KafkaListener(topics = "sample", groupId = "group_id")
    public void listenGroupFoo(String message) {
        System.out.println("Received Message in group foo: " + message);
    }
}
