package com.crud.crud.DTOS;

import javax.validation.constraints.NotBlank;

public record AuthenticationDTO(
        @NotBlank
        String email,
        @NotBlank
        String password
) {
}
