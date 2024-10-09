package org.okten.springdemo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.okten.springdemo.dto.ReviewDTO;
import org.okten.springdemo.entity.Review;

import java.time.LocalDateTime;

@Mapper(imports = LocalDateTime.class)
public interface ReviewMapper {

    ReviewDTO mapToDto(Review review);

    @Mapping(target = "timestamp", expression = "java(LocalDateTime.now())")
    Review mapToEntity(ReviewDTO reviewDTO);
}
