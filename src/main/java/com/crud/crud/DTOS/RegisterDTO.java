package com.crud.crud.DTOS;

import com.crud.crud.user.enums.UserRole;

import javax.validation.constraints.NotBlank;

public record RegisterDTO(
        @NotBlank
        String email,
        @NotBlank
        String password,
        @NotBlank
        UserRole role
) {
}
