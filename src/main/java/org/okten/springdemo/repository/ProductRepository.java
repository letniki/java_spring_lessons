package org.okten.springdemo.repository;

import org.okten.springdemo.entity.Product;
import org.okten.springdemo.entity.ProductAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByPriceBetween(Double minPrice, Double maxPrice);

    @Query("select p from Product p where p.price > :minPrice and p.price < :maxPrice") // JPQL
    List<Product> findAllByPriceBetweenWithJpql(Double minPrice, Double maxPrice);

    @Query(value = "select * from products p where p.price > :minPrice and p.price < :maxPrice", nativeQuery = true) // SQL
    List<Product> findAllByPriceBetweenWithSql(Double minPrice, Double maxPrice);

    List<Product> findAllByPriceGreaterThan(Double minPrice);

    List<Product> findAllByPriceLessThan(Double maxPrice);
}
