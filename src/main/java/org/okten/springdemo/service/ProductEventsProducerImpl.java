package org.okten.springdemo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.okten.springdemo.dto.ProductDeletedEvent;
import org.okten.springdemo.event.dto.ProductDeletedPayload;
import org.okten.springdemo.event.producer.IProductEventsProducer;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductEventsProducerImpl {

    private final IProductEventsProducer eventsProducer;

public void produce(ProductDeletedEvent event) {
    log.info("Producing event: {}", event);
    eventsProducer.productDeleted(new ProductDeletedPayload()
            .withProductId(event.productId().intValue()));
}
}
