package com.hemant.Springdatajpa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hemant.Springdatajpa.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
//	List<Product> findByCategory();

	List<Product> findByProductNameContaining(String productName);
}
