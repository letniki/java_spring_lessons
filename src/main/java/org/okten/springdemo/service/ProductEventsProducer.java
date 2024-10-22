package org.okten.springdemo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.okten.springdemo.dto.ProductDeletedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductEventsProducer {
    private final KafkaTemplate<Integer, ProductDeletedEvent> productDeletedEventProducer;

    @Value("${spring.kafka.producer.topic}")
    private String productDeletedEventTopic;

public void produce(ProductDeletedEvent event) {
    log.info("Producing event: {}", event);
    productDeletedEventProducer.send(productDeletedEventTopic, event);
}
}
