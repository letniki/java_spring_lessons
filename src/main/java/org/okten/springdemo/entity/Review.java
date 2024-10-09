package org.okten.springdemo.entity;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Data
@Builder
@Document("reviews")
public class Review {

    @MongoId
    private ObjectId id;

    private Long productId;

    private Integer rating;

    private String text;

    private LocalDateTime timestamp;
}
