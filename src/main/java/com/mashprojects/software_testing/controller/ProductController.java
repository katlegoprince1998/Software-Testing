package com.mashprojects.software_testing.controller;

import com.mashprojects.software_testing.dto.ProductDto;
import com.mashprojects.software_testing.exceptions.ProductNotFoundException;
import com.mashprojects.software_testing.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/product")
@RestController
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final ProductService service;
    public ProductController(ProductService service){
        this.service = service;
    }
    @PostMapping("/create")
    public ResponseEntity<ProductDto> create(@RequestBody ProductDto dto){
        try{
            logger.info("Creating a new product: {}", dto.getName());
            ProductDto createdProduct = service.createProduct(dto);
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) throws ProductNotFoundException {
        logger.info("Fetching product with id: {}", id);
        return service.getProductById(id);
    }

    @GetMapping
    public List<ProductDto> getProducts() {
        logger.info("Fetching all products");
        return service.getProducts();
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) throws ProductNotFoundException {
        logger.info("Deleting product with id: {}", id);
        return service.deleteProduct(id);
    }

    @PutMapping("/{id}")
    public String updateProduct(@RequestBody ProductDto productDto, @PathVariable Long id) throws ProductNotFoundException {
        logger.info("Updating product with id: {}", id);
        return service.updateProduct(productDto, id);
    }
}
