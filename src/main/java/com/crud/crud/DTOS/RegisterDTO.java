package com.crud.crud.DTOS;

import javax.validation.constraints.NotBlank;

import com.crud.crud.user.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public record RegisterDTO(
    @NotBlank String email,
    @NotBlank String password,
    @NotBlank UserRole role) {
}
