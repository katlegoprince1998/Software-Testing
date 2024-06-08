package com.mashprojects.software_testing.service;

import com.mashprojects.software_testing.dto.ProductDto;
import com.mashprojects.software_testing.entity.Product;
import com.mashprojects.software_testing.exceptions.ProductNotFoundException;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto dto);
    ProductDto getProductById(Long id) throws ProductNotFoundException;
    List<ProductDto> getProducts();
    String deleteProduct(Long id) throws ProductNotFoundException;
    String updateProduct(ProductDto product, Long id) throws ProductNotFoundException;
}
