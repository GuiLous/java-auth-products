package com.crud.crud.product.controllers;

import com.crud.crud.product.models.ProductModel;
import com.crud.crud.DTOS.RequestProductDTO;
import com.crud.crud.product.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("product")
public class ProductsController {
    @Autowired
    ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        return this.productService.getAllProducts();
    }

    @PostMapping
    public ResponseEntity<ProductModel> createProduct(@RequestBody @Valid RequestProductDTO data) {
        return this.productService.createProduct(data);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable UUID id, @RequestBody @Valid RequestProductDTO data) {
        return this.productService.updateProduct(id, data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable UUID id) {
        return this.productService.deleteProduct(id);
    }
}
