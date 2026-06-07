package com.authapi.controllers;

import com.authapi.dtos.UserRegistrationDto;
import com.authapi.dtos.UserResponseDto;
import com.authapi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;

    }

    @PostMapping
    public ResponseEntity<UserResponseDto> cadastrar(@RequestBody @Valid UserRegistrationDto dto) {
        // @valid aciona as validaçoes que colocamos no record.

        UserResponseDto response = userService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> buscarTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.buscarPorId(id));
    }

    @PutMapping
    public ResponseEntity<UserResponseDto> alterar(@PathVariable UUID id , @RequestBody @Valid UserRegistrationDto dto){
        return ResponseEntity.ok(userService.alterar(id, dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id){
        userService.deletar(id);
        return ResponseEntity.noContent().build();
        // Retorna Status 204 (No Content) após deletar
    }
}