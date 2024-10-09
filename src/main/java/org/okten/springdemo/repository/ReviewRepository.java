package org.okten.springdemo.repository;

import org.bson.types.ObjectId;
import org.okten.springdemo.entity.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface ReviewRepository extends MongoRepository<Review, ObjectId> {

    List<Review> findAllByProductId(Long productId);

    List<Review> findAllByTimestampAfter(LocalDateTime startDateTime);
}
