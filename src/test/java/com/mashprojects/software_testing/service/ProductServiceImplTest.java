package com.mashprojects.software_testing.service;


import com.mashprojects.software_testing.dto.ProductDto;
import com.mashprojects.software_testing.entity.Product;
import com.mashprojects.software_testing.exceptions.ProductNotFoundException;
import com.mashprojects.software_testing.mapper.ProductMapper;
import com.mashprojects.software_testing.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {
    @Mock
    private ProductMapper mapper;
    @Mock
    private ProductRepository repository;
    @InjectMocks
    private ProductServiceImpl service;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct(){
        Product product = new Product();
        Product savedProduct = new Product();
        ProductDto dto = new ProductDto();
        ProductDto savedProductDto = new ProductDto();

        when(mapper.toProduct(dto)).thenReturn(product);
        when(repository.save(product)).thenReturn(savedProduct);
        when(mapper.toProductDto(savedProduct)).thenReturn(savedProductDto);

        ProductDto result = service.createProduct(dto);

        assertEquals(savedProductDto, result);
        verify(mapper).toProduct(dto);
        verify(repository).save(product);
        verify(mapper).toProductDto(savedProduct);

    }

    @Test
    void testGetProductById_success() throws ProductNotFoundException{
        Long id = 1L;
        ProductDto dto = new ProductDto();
        Product product = new Product();

        when(repository.findById(id)).thenReturn(Optional.of(product));
        when(mapper.toProductDto(product)).thenReturn(dto);

        ProductDto result = service.getProductById(id);

        assertEquals(dto, result);
        verify(repository).findById(id);
        verify(mapper).toProductDto(product);
    }
    @Test
    void testGetProductById_notFound(){
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> {
                 service.getProductById(id);
                });
        verify(repository).findById(id);
        verify(mapper, never()).toProductDto(any());

    }
    @Test
    void testGetAlProducts_EmptyList(){
        when(repository.findAll()).thenReturn(List.of());

        List<ProductDto> result = service.getProducts();

        assertTrue(result.isEmpty());
        verify(repository).findAll();
        verify(mapper, never()).toProductDto(any());

    }

    @Test
    void testGetAllProducts_NonEmptyList(){
        Product product1 = new Product();
        Product product2 = new Product();
        ProductDto dto1 = new ProductDto();
        ProductDto dto2 = new ProductDto();

        when(repository.findAll()).thenReturn(List.of(product1, product2));
        when(mapper.toProductDto(product1)).thenReturn(dto1);
        when(mapper.toProductDto(product2)).thenReturn(dto2);

        List<ProductDto> result = service.getProducts();

        assertEquals(2, result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));
        verify(repository).findAll();
        verify(mapper).toProductDto(product1);
        verify(mapper).toProductDto(product2);
    }

    @Test
    void testDeleteProduct_success() throws ProductNotFoundException{
        Product product = new Product();
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.of(product));

        String result = service.deleteProduct(id);

        assertEquals("Product Successfully Deleted", result);
        verify(repository).findById(id);
        verify(repository).delete(product);
    }
    @Test
    void testDeleteProduct_failed(){
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

         assertThrows(ProductNotFoundException.class,
                 () -> {
             service.deleteProduct(id);
                 });
         verify(repository).findById(id);
         verify(repository, never()).delete(any());
    }
   @Test
    void testUpdateProduct_Success() throws ProductNotFoundException{
       Long id = 1L;
       Product existingProduct = new Product();
       existingProduct.setDescription("Best Monitor");
       existingProduct.setName("Dell");
       existingProduct.setPrice("8000");
       existingProduct.setExpiryDate("3030");
       existingProduct.setId(id);
       ProductDto updatedProduct = new ProductDto();
        updatedProduct.setDescription("Bad Monitor");
        updatedProduct.setName("Mac");
        updatedProduct.setPrice("8999");
        updatedProduct.setExpiryDate("3046");


        when(repository.findById(id)).thenReturn(Optional.of(existingProduct));
        when(repository.save(existingProduct)).thenReturn(existingProduct);

        // Act
        String result = service.updateProduct(updatedProduct, id);

        // Assert
        assertEquals("Product Updated Successfully", result);
        verify(repository).findById(id);
        verify(repository).save(existingProduct);
        assertEquals("Bad Monitor", existingProduct.getDescription());
        assertEquals("8999", existingProduct.getPrice());
        assertEquals("Mac", existingProduct.getName());
        assertEquals("3046", existingProduct.getExpiryDate());
    }

    @Test
    void testUpdateProduct_NotFound() {
        // Arrange
        Long productId = 1L;
        ProductDto updatedProduct = new ProductDto(); // Create a sample updated Product object

        when(repository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> {
            service.updateProduct(updatedProduct, productId);
        });
        verify(repository).findById(productId);
        verify(repository, never()).save(any());
    }
}