package com.crud.crud.product.services;

import com.crud.crud.DTOS.RequestProductDTO;
import com.crud.crud.product.models.ProductModel;
import com.crud.crud.product.repositories.IProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    IProductRepository productRepository;

    public ResponseEntity<List<ProductModel>> getAllProducts() {
        var allProducts = this.productRepository.findAll();

        return ResponseEntity.ok(allProducts);
    }

    public ResponseEntity<ProductModel> createProduct(@RequestBody @Valid RequestProductDTO data) {
        ProductModel newProduct = new ProductModel(data);

        var createdProduct = this.productRepository.save(newProduct);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable UUID id, @RequestBody @Valid RequestProductDTO data) {
        ProductModel product = this.productRepository.findById(id).orElseThrow();

        product.setName(data.name());
        product.setPrice_in_cents(data.price_in_cents());

        this.productRepository.save(product);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable UUID id) {
        ProductModel product = this.productRepository.findById(id).orElseThrow();

        this.productRepository.deleteById(product.getId());

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
