package com.projeto_spring_security.api.dtos;

import com.projeto_spring_security.api.enums.Role;

public record UsuarioResponseDTO(
        String username,
        String email,
        Role role
) {
}
