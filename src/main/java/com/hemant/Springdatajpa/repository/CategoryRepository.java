package com.hemant.Springdatajpa.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.hemant.Springdatajpa.model.Category;
//import com.hemant.Springdatajpa.model.Product;

public interface CategoryRepository extends JpaRepository<Category, Long> {

//	List<Product> findByProductNameContaining(String productName);

	Page<Category> findByProductId(Long productId, Pageable pageable);

	Optional<Category> findByIdAndProductId(Long categoryId, Long productId);

}
