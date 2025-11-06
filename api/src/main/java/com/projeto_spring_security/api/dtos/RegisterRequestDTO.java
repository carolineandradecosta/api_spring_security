package com.projeto_spring_security.api.dtos;

import com.projeto_spring_security.api.enums.Role;

public record RegisterRequestDTO(
        String username,
        String password,
        String email,
        Role role
) {
}
