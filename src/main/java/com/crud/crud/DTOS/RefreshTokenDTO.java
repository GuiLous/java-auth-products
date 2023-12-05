package com.crud.crud.DTOS;

import javax.validation.constraints.NotEmpty;

public record RefreshTokenDTO(@NotEmpty String refreshToken) {

}
