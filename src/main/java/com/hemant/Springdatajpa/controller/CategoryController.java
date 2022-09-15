package com.hemant.Springdatajpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hemant.Springdatajpa.exception.ResourceNotFoundException;
import com.hemant.Springdatajpa.model.Category;
import com.hemant.Springdatajpa.repository.CategoryRepository;
import com.hemant.Springdatajpa.repository.ProductRepository;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products/{productId}/category")
    public Page<Category> getAllCommentsByPostId(@PathVariable (value = "productId") Long productd, Pageable pageable) {
        return categoryRepository.findByProductId(productd, pageable);
    }

    @PostMapping("/products/{productId}/category")
    public Category createCategory(@PathVariable (value = "productId") Long productId, @RequestBody Category category) {
        return productRepository.findById(productId).map(product -> {
        	category.setProduct(null);
            return categoryRepository.save(category);
        }).orElseThrow(() -> new ResourceNotFoundException("ProductId " + productId + " not found"));
    }

    @PutMapping("/products/{productId}/category/{categoryId}")
    public Category updateComment(@PathVariable (value = "productId") Long productId,
                                 @PathVariable (value = "categoryId") Long categoryId,
                                 @RequestBody Category categoryRequest) {
        if(!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("ProductId " + productId + " not found");
        }

        return categoryRepository.findById(categoryId).map(category -> {
            category.setGST(categoryRequest.getGST());
            return categoryRepository.save(category);
        }).orElseThrow(() -> new ResourceNotFoundException("CategoryId " + categoryId + "not found"));
    }

    @DeleteMapping("/products/{productId}/category/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable (value = "productId") Long productId,
                              @PathVariable (value = "categoryId") Long categoryId) {
        return categoryRepository.findByIdAndProductId(categoryId, productId).map(category -> {
            categoryRepository.delete(category);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + categoryId + " and productId " + productId));
    }
}
