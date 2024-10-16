package org.okten.springdemo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.okten.springdemo.api.dto.ProductDto;
import org.okten.springdemo.dto.ReviewDTO;
import org.okten.springdemo.entity.Product;
import org.okten.springdemo.entity.Review;
import org.okten.springdemo.mapper.ProductMapper;
import org.okten.springdemo.mapper.ReviewMapper;
import org.okten.springdemo.repository.ProductRepository;
import org.okten.springdemo.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final ReviewRepository reviewRepository;
	private final ReviewMapper reviewMapper;
	private final ProductMapper productMapper;

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

	public ProductDto createProduct(ProductDto productDto) {
		if (productDto.getId() != null) {
			throw new IllegalArgumentException("Product can not be create with pre-defined id");
		}

		Product product = this.productMapper.mapToEntity(productDto);
		Product savedProduct = productRepository.save(product);

		return this.productMapper.mapToDto(savedProduct);
	}

	public void deleteProduct(Long productId) {
		if (!this.productRepository.existsById(productId)) {
			throw new IllegalArgumentException("Product with id " + productId + " does not exist");
		}

		this.productRepository.deleteById(productId);
	}

	public Optional<ProductDto> findProduct(Long productId) {
		return productRepository.findById(productId).map(productMapper::mapToDto);
	}

	public List<ProductDto> getAllProducts() {
		return this.productRepository.findAll()
				.stream()
				.map(productMapper::mapToDto)
				.toList();
	}

	@Transactional
	public Optional<ProductDto> updateProduct(Long productId, ProductDto productDtoUpdateWith) {
		return productRepository.findById(productId)
				.map(existingProduct -> productMapper.updateEntity(existingProduct, productDtoUpdateWith))
				.map(productMapper::mapToDto);
	}

	@Transactional
	public Optional<ProductDto> updateProductPartially(Long productId, ProductDto productDtoUpdateWith) {
		return productRepository.findById(productId)
				.map(existingProduct -> productMapper.updateEntityPartially(existingProduct, productDtoUpdateWith))
				.map(productMapper::mapToDto);
	}
}
