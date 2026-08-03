package com.ecommerce.project.repositories;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByCategoryOrderByPriceAsc(Category category, PageRequest pageRequest);

    Page<Product> findByProductNameLikeIgnoreCase(String s, PageRequest pageRequest);

    boolean existsByCategoryCategoryIdAndProductName(Long categoryId, @NotBlank @Size(min = 3, message = "minimum 3 characters required.") String productName);
}
