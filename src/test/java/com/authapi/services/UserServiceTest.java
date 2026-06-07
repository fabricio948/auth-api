package com.authapi.services;
import com.authapi.dtos.UserRegistrationDto;
import com.authapi.dtos.UserResponseDto;
import com.authapi.models.User;
import com.authapi.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Deve cadastrar um usuário com sucesso quando os dados forem válidos")
    void cadastrarCenario1() {
        // AAA - Arrange (Preparar o cenário)
        UserRegistrationDto dto = new UserRegistrationDto("Lincoln", "lincoln@email.com", "senha123");

        User userSalvo = new User();
        userSalvo.setId(UUID.randomUUID());
        userSalvo.setNome(dto.nome());
        userSalvo.setEmail(dto.email());
        userSalvo.setSenha("senhaCriptografadaNoMock");

        // Simulando que o e-mail está livre e que o banco vai salvar com sucesso
        when(userRepository.findByEmail(dto.email())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(userSalvo);

        // Act (Executar a ação)
        UserResponseDto resultado = userService.cadastrar(dto);

        // Assert (Verificar as expectativas - Clean Code)
        assertNotNull(resultado);
        assertNotNull(resultado.id());
        assertEquals(dto.nome(), resultado.nome());
        assertEquals(dto.email(), resultado.email());

        // Garante que o método save do repositório foi chamado exatamente 1 vez
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Deve lançar RuntimeException quando tentar cadastrar e-mail duplicado")
    void cadastrarCenario2() {
        // Arrange
        UserRegistrationDto dto = new UserRegistrationDto("Fabricio", "duplicado@email.com", "senha123");
        User userExistente = new User(); // Simula que já existe alguém com esse e-mail

        when(userRepository.findByEmail(dto.email())).thenReturn(Optional.of(userExistente));

        // Act & Assert (Em testes de exceção, rodamos e verificamos juntos)
        RuntimeException excecao = assertThrows(RuntimeException.class, () -> {
            userService.cadastrar(dto);
        });

        assertEquals("E-mail já cadastrado no sistema.", excecao.getMessage());

        // SOLID/Clean Code: Se deu erro no e-mail, o método save NUNCA deve ter sido chamado
        verify(userRepository, never()).save(any(User.class));
    }
}
