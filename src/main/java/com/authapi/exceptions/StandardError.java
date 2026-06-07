package com.authapi.exceptions;

import java.time.Instant;
// Record para garantir imutabilidade e clareza nos dados de erro que saem da API
public record StandardError(
        Instant timestamp,
        Integer status,
        String error,
        String message,
        String path
) {
}
