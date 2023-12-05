package com.crud.crud.product.controllers;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.crud.DTOS.RequestProductDTO;
import com.crud.crud.product.models.ProductModel;
import com.crud.crud.product.services.ProductService;

@RestController
@RequestMapping("product")
public class ProductsController {
    @Autowired
    ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductModel>> getAllProducts() throws Exception {
        return this.productService.getAllProducts();
    }

    @PostMapping
    public ResponseEntity<ProductModel> createProduct(@RequestBody @Valid RequestProductDTO data) throws Exception {
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
