package org.okten.springdemo.controller;

import lombok.RequiredArgsConstructor;
import org.okten.springdemo.api.controller.ProductsApi;
import org.okten.springdemo.api.dto.ProductDto;
import org.okten.springdemo.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductsApi {

    private final ProductService productService;

    @Override
    public ResponseEntity<ProductDto> createProduct(ProductDto productDto) {
        return ResponseEntity.ok(productService.createProduct(productDto));
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ProductDto> getProduct(Long id) {
        return ResponseEntity.of(productService.findProduct(id));
    }

    @Override
    public ResponseEntity<List<ProductDto>> getProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @Override
    public ResponseEntity<ProductDto> modifyProduct(Long id, ProductDto productDto) {
        return ResponseEntity.of(productService.updateProduct(id, productDto));
    }

    @Override
    public ResponseEntity<ProductDto> modifyProductPartially(Long id, ProductDto productDto) {
        return ResponseEntity.of(productService.updateProductPartially(id, productDto));
    }
}
