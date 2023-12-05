package com.crud.crud.product.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crud.crud.product.models.ProductModel;

public interface IProductRepository extends JpaRepository<ProductModel, UUID> {
    void deleteById(UUID id);
}
