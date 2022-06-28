package com.malyshev.demojwt.dto;

import lombok.Data;

@Data
public class TokenDto {
    String token;
    public TokenDto(String token) {
        this.token = token;
    }
}
