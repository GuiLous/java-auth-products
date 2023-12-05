package com.crud.crud.user.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crud.crud.user.models.UserModel;

public interface IUserRepository extends JpaRepository<UserModel, UUID> {
    UserModel findByEmail(String email);
}
