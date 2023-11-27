package com.crud.crud.user.repositories;

import com.crud.crud.user.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface IUserRepository extends JpaRepository<UserModel, UUID> {
    UserDetails findByEmail(String email);
}
