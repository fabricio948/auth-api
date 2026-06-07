package com.authapi.services;

import com.authapi.dtos.UserRegistrationDto;
import com.authapi.dtos.UserResponseDto;
import com.authapi.models.User;
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

        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new RuntimeException("E-mail já cadastrado no sistema.");
        }
        User user = new User();
        user.setNome(dto.nome());
        user.setEmail(dto.email());

        // Regra de Segurança: Criptofrafa a senha com BCrypt antes de salvar
        user.setSenha(passwordEncoder.encode(dto.senha()));

        User savedUser = userRepository.save(user);
        return new UserResponseDto(savedUser);
    }

    @Override
    public List<UserResponseDto> buscarTodos() {
        // Uso de Stream API: Transforma de forma funcional a lista de User em UserResponseDto
        return userRepository.findAll().stream()
                .map(UserResponseDto::new)
                .toList();

    }

    @Override
    public UserResponseDto buscarPorId(UUID id) {
        // Solid: Lança exceção caso nao encontre, delegando o erro para o Handler Gobla
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado com o ID:"));
             return new UserResponseDto(user);

    }

    @Override
    public UserResponseDto alterar(UUID id, UserRegistrationDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Usuario nao encontrado para atualização"));
        if(!user.getEmail().equals(dto.email()) && userRepository.findByEmail(dto.email()).isPresent()) {
            throw new RuntimeException("E-mail já cadastrado no sistema.");
        }
        user.setNome(dto.nome());
        user.setEmail(dto.email());
        user.setSenha(passwordEncoder.encode(dto.senha()));

        return new UserResponseDto(userRepository.save(user));
    }

    @Override
    public void deletar(UUID id) {
    User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario nao encontrado para exclusão"));
    userRepository.delete(user);
    }

    /**
     * Exemplo de uso do 'instanceof' moderno (Pattern Matching) solicitado.
     * Útil para validar objetos de forma genérica mantendo o Clean Code.
     */

    public void verificarTipoObjeto(Object obj){
        // java moderno: Se 'obj' for instancia de User, ele já cria a variavel 'u' automaticamente
        if(obj instanceof User u){
            System.out.println("Processando usuario no log" + u.getEmail());
        }
   }
}