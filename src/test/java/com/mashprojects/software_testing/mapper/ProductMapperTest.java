package com.mashprojects.software_testing.mapper;


import com.mashprojects.software_testing.dto.ProductDto;
import com.mashprojects.software_testing.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductMapperTest {
   private ProductMapper mapper;

   @BeforeEach
   void setUp(){
       mapper = new ProductMapper();
   }

   @Test
    void shouldMapDtoToProduct(){
       ProductDto dto = ProductDto
               .builder()
               .description("The best monitor in the world")
               .expiryDate("2052")
               .price("5000")
               .name("Dell 28 Inch HD Monitor")
               .id(1L)
               .build();

       Product product = mapper.toProduct(dto);

       assertEquals(dto.getExpiryDate(), product.getExpiryDate());
       assertEquals(dto.getName(), product.getName());
       assertEquals(dto.getPrice(), product.getPrice());
       assertEquals(dto.getDescription(), product.getDescription());
   }
   @Test
   void shouldMapProductToDto(){
       Product product = Product
               .builder()
               .id(1L)
               .description("Best monitor")
               .expiryDate("2028")
               .name("Samsung")
               .price("30000")
               .build();

       ProductDto productDto = mapper.toProductDto(product);
       assertEquals(product.getDescription(), productDto.getDescription());
       assertEquals(product.getId(), productDto.getId());
       assertEquals(product.getName(), productDto.getName());
       assertEquals(product.getExpiryDate(), productDto.getExpiryDate());
       assertEquals(product.getPrice(), productDto.getPrice());
   }
   @Test
    public void should_throw_null_pointer_exception_when_product_dto_is_null(){
       var exception = assertThrows(NullPointerException.class, () -> mapper.toProduct(null));
       assertEquals("DTO should not be null", exception.getMessage());
   }
}