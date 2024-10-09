package org.okten.springdemo.service;

import lombok.RequiredArgsConstructor;
import org.okten.springdemo.dto.ReviewDTO;
import org.okten.springdemo.entity.Review;
import org.okten.springdemo.mapper.ReviewMapper;
import org.okten.springdemo.repository.ProductRepository;
import org.okten.springdemo.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final ReviewRepository reviewRepository;
	private final ReviewMapper reviewMapper;

	public ReviewDTO createReview(Long productId, ReviewDTO reviewDTO) {
		if (!this.productRepository.existsById(productId)) {
			throw new IllegalArgumentException("Product with id " + productId + " does not exist");
		}

		final Review review = this.reviewMapper.mapToEntity(reviewDTO);
		review.setProductId(productId);
		review.setTimestamp(LocalDateTime.now());
		return this.reviewMapper.mapToDto(this.reviewRepository.save(review));
	}

	public List<ReviewDTO> findAllReviewsForProduct(Long productId) {
		return this.reviewRepository
				.findAllByProductId(productId)
				.stream()
				.map(this.reviewMapper::mapToDto)
				.toList();
	}

	public List<ReviewDTO> getLatestReviews(LocalDateTime startDateTime) {
		return this.reviewRepository
				.findAllByTimestampAfter(startDateTime)
				.stream()
				.map(this.reviewMapper::mapToDto)
				.toList();
	}
}
