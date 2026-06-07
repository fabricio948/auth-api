package com.authapi.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 1. Captura as nossas exceções de Regra de Negócio (RuntimeExceptions que lançamos no Service)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<StandardError> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        // Exemplo simples de Switch Expression para ajustar o status caso o usuário não seja encontrado
        if (e.getMessage().contains("não encontrado")) {
            status = HttpStatus.NOT_FOUND;
        }

        StandardError err = new StandardError(
                Instant.now(),
                status.value(),
                "Regra de Negócio Violada",
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(err);
    }

        // 2. Captura erros de validação do @Valid (ex: e-mail sem @ ou senha curta)
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<StandardError> handleValidationException(MethodArgumentNotValidException e, HttpServletRequest request) {
            HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY; // Status 422

            // Uso da Stream API para juntar todas as mensagens de campos inválidos em um único texto
            String mensagensDeErro = e.getBindingResult().getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining(" | "));

            StandardError err = new StandardError(
                    Instant.now(),
                    status.value(),
                    "Erro de Validação nos Dados",
                    mensagensDeErro,
                    request.getRequestURI()
            );

            return ResponseEntity.status(status).body(err);
        }
}
