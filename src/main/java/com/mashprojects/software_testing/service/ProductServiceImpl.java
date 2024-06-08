package com.mashprojects.software_testing.service;

import com.mashprojects.software_testing.dto.ProductDto;
import com.mashprojects.software_testing.entity.Product;
import com.mashprojects.software_testing.exceptions.ProductNotFoundException;
import com.mashprojects.software_testing.mapper.ProductMapper;
import com.mashprojects.software_testing.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductServiceImpl(ProductRepository repository, ProductMapper mapper){
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public ProductDto createProduct(ProductDto dto) {
        var product = mapper.toProduct(dto);
        var saveProduct = repository.save(product);
        return mapper.toProductDto(saveProduct);

    }

    @Override
    public ProductDto getProductById(Long id) throws ProductNotFoundException {
        var product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product was not found"));
        return mapper.toProductDto(product);
    }

    @Override
    public List<ProductDto> getProducts() {
        var products = repository.findAll();
        return products.stream().map(mapper::toProductDto).toList();
    }

    @Override
    public String deleteProduct(Long id) throws ProductNotFoundException {
        var product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product was not found"));
        repository.delete(product);
        return "Product Successfully Deleted";
    }

    @Override
    public String updateProduct(ProductDto product, Long id) throws ProductNotFoundException {
        Product existingProduct = repository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product Was Not Found"));
        existingProduct.setExpiryDate(product.getExpiryDate());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        repository.save(existingProduct);
        return "Product Updated Successfully";
    }
}
