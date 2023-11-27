package com.crud.crud.product.repositories;

import com.crud.crud.product.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IProductRepository extends JpaRepository<ProductModel, UUID> {}
