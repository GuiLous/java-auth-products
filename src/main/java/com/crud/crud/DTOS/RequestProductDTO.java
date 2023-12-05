package com.crud.crud.DTOS;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record RequestProductDTO(
    @NotBlank String name,
    @NotNull Integer price_in_cents) {
}
