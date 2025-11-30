package com.ecommerce.project.service;

import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;

public interface ProductService {
    ProductDTO saveProduct(Product product, Long categoryId);

    ProductResponse getAllProducts();

    ProductResponse searchProductsByCategory(Long categoryId);
}
