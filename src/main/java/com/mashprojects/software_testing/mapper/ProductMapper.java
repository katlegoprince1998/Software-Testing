package com.mashprojects.software_testing.mapper;

import com.mashprojects.software_testing.dto.ProductDto;
import com.mashprojects.software_testing.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    // map product from dto to entity
    public Product toProduct(ProductDto dto){
        if (dto == null){
            throw new NullPointerException("DTO should not be null");
        }
        var product = new Product();
        product.setDescription(dto.getDescription());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setExpiryDate(dto.getExpiryDate());

        return product;
    }

    public ProductDto toProductDto(Product product){
        var productDto = new ProductDto();
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setExpiryDate(product.getExpiryDate());
        return productDto;
    }
}
