package com.authapi.services;



import com.authapi.dtos.UserRegistrationDto;
import com.authapi.dtos.UserResponseDto;
import java.util.List;
import java.util.UUID;


public interface UserService {
    UserResponseDto cadastrar(UserRegistrationDto dto);
    List<UserResponseDto> buscarTodos();
    UserResponseDto buscarPorId(UUID id);
    UserResponseDto alterar(UUID id, UserRegistrationDto dto);
    void deletar(UUID id);
}
