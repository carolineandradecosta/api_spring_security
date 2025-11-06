package com.projeto_spring_security.api.dtos;

public record LoginRequestDTO(
        String username,
        String password
) {
}
