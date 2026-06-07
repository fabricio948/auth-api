package com.authapi.services;

import com.authapi.dtos.UserRegistrationDto;
import com.authapi.dtos.UserResponseDto;
import com.authapi.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // Construtor para injeção de dependência (Boa prática do Spring/SOLID em vez de @Autowired)
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserResponseDto cadastrar(UserRegistrationDto dto) {
        // Implementaremos a lógica de cadastro e validação no próximo passo!
        return null;
    }

    @Override
    public List<UserResponseDto> buscarTodos() { return List.of(); }

    @Override
    public UserResponseDto buscarPorId(UUID id) { return null; }

    @Override
    public UserResponseDto alterar(UUID id, UserRegistrationDto dto) { return null; }

    @Override
    public void deletar(UUID id) {}
}
