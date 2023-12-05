package com.crud.crud.product.services;

import com.crud.crud.DTOS.RequestProductDTO;
import com.crud.crud.product.models.ProductModel;
import com.crud.crud.product.repositories.IProductRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@DataJpaTest
@ActiveProfiles("test")
class ProductServiceTest {
    @Mock
    IProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return all products from DB")
    void getAllProducts() throws Exception {
        RequestProductDTO product1 = new RequestProductDTO("test", 100);
        RequestProductDTO product2 = new RequestProductDTO("test2", 200);

        when(productRepository.findAll()).thenReturn(List.of(new ProductModel(product1), new ProductModel(product2)));

        ResponseEntity<List<ProductModel>> result = this.productService.getAllProducts();

        assertThat(Objects.requireNonNull(result.getBody()).size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Should create a product and save on DB")
    void createProduct() throws Exception {
        RequestProductDTO product1 = new RequestProductDTO("test", 100);
        ProductModel responseProduct = new ProductModel(product1);

        when(productRepository.save(any(ProductModel.class))).thenReturn(responseProduct);

        ResponseEntity<ProductModel> response = this.productService.createProduct(product1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(responseProduct);
    }

    @Test
    @DisplayName("Should update a product by id")
    void updateProduct() throws Exception {
        RequestProductDTO requestProductDTO = new RequestProductDTO("updated product", 100);

        UUID productId = UUID.randomUUID();
        ProductModel existingProduct = new ProductModel();
        existingProduct.setId(productId);
        existingProduct.setName("old Product");
        existingProduct.setPrice_in_cents(50);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        ResponseEntity<Object> response = this.productService.updateProduct(productId, requestProductDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(existingProduct.getName()).isEqualTo("updated product");
        assertThat(existingProduct.getPrice_in_cents()).isEqualTo(100);
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    @DisplayName("Should delete a product by id")
    void deleteProduct() throws Exception {
        UUID productId = UUID.randomUUID();

        this.productRepository.deleteById(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }
}