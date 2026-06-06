package com.authapi.dtos;

import com.authapi.models.User;

import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String nome,
        String email
) {
    // Construtor compacto para transformar facilmente um Model em um DTO de saída
    public UserResponseDto(User user) {
        this(user.getId(), user.getNome(), user.getEmail());
    }
}
