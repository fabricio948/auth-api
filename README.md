# Auth API

API REST de autenticação e gerenciamento de usuários desenvolvida com **Spring Boot 3.5.3** e **Java 21**, seguindo os princípios de **Monolito Modular**, **SOLID** e **Clean Code**.

## 🚀 Tecnologias

| Tecnologia | Versão | Finalidade |
|------------|--------|------------|
| Java | 21 | Records, Pattern Matching, Switch Expressions |
| Spring Boot | 3.5.3 | Framework principal |
| Spring Data JPA | - | Persistência com Hibernate |
| Spring Security | - | Criptografia de senhas (BCrypt) |
| Spring Validation | - | Validação de dados de entrada |
| H2 Database | - | Banco de dados em memória (dev) |
| SpringDoc OpenAPI | 2.8.9 | Documentação Swagger UI |
| Lombok | - | Redução de boilerplate |
| Mockito | - | Testes unitários |
| Maven | 3.8.7 | Gerenciamento de dependências |

## 📁 Estrutura do Projeto

```
auth-api/
├── .cline/                          # Documentação do projeto (Cline)
│   ├── projectbrief.md              # Resumo e objetivos
│   ├── systemPatterns.md            # Padrões de arquitetura
│   └── techContext.md               # Contexto técnico
├── src/
│   ├── main/
│   │   ├── java/com/authapi/
│   │   │   ├── AuthApiApplication.java        # Classe principal
│   │   │   ├── config/
│   │   │   │   └── SecurityConfig.java        # Configuração de segurança
│   │   │   ├── controllers/
│   │   │   │   └── UserController.java        # Endpoints REST
│   │   │   ├── dtos/
│   │   │   │   ├── LoginRequestDto.java       # DTO de login
│   │   │   │   ├── UserRegistrationDto.java   # DTO de cadastro
│   │   │   │   └── UserResponseDto.java       # DTO de resposta
│   │   │   ├── exceptions/
│   │   │   │   ├── GlobalExceptionHandler.java # Tratamento global de erros
│   │   │   │   └── StandardError.java         # Formato padronizado de erro
│   │   │   ├── models/
│   │   │   │   └── User.java                  # Entidade JPA
│   │   │   ├── repositories/
│   │   │   │   └── UserRepository.java        # Repositório JPA
│   │   │   └── services/
│   │   │       ├── UserService.java           # Interface do serviço
│   │   │       └── UserServiceImpl.java       # Implementação do serviço
│   │   └── resources/
│   │       └── application.yml                # Configurações da aplicação
│   └── test/
│       └── java/com/authapi/
│           ├── AuthApiApplicationTests.java   # Teste de contexto
│           └── services/
│               └── UserServiceTest.java       # Testes unitários do serviço
├── pom.xml                        # Configuração Maven
└── README.md                      # Este arquivo
```

## 🔧 Como Executar

### Pré-requisitos

- **Java 21** instalado
- **Maven 3.8+** instalado

### Passos

```bash
# 1. Compilar o projeto
mvn clean compile

# 2. Executar os testes
mvn test

# 3. Iniciar a aplicação
mvn spring-boot:run
```

A aplicação será iniciada em `http://localhost:8080`.

## 📡 Endpoints da API

### Usuários (`/api/users`)

| Método | Rota | Descrição | Status HTTP |
|--------|------|-----------|-------------|
| `POST` | `/api/users` | Cadastrar novo usuário | `201 Created` |
| `GET` | `/api/users` | Listar todos os usuários | `200 OK` |
| `GET` | `/api/users/{id}` | Buscar usuário por ID | `200 OK` |
| `PUT` | `/api/users/{id}` | Alterar dados do usuário | `200 OK` |
| `DELETE` | `/api/users/{id}` | Excluir usuário | `204 No Content` |

### Exemplos de Requisição

#### Cadastrar usuário
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "email": "joao@email.com",
    "senha": "senha123"
  }'
```

#### Listar todos
```bash
curl http://localhost:8080/api/users
```

#### Buscar por ID
```bash
curl http://localhost:8080/api/users/{id}
```

#### Alterar usuário
```bash
curl -X PUT http://localhost:8080/api/users/{id} \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva Atualizado",
    "email": "joao.novo@email.com",
    "senha": "novaSenha123"
  }'
```

#### Excluir usuário
```bash
curl -X DELETE http://localhost:8080/api/users/{id}
```

## 📖 Documentação Interativa (Swagger)

Após iniciar a aplicação, acesse:

- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **OpenAPI JSON:** http://localhost:8080/api-docs

## 🗄️ Console do Banco H2

Disponível em: http://localhost:8080/h2-console

- **JDBC URL:** `jdbc:h2:mem:authdb`
- **Usuário:** `sa`
- **Senha:** *(vazio)*

## 🧪 Testes

```bash
mvn test
```

O projeto possui **6 testes unitários** que cobrem:

- ✅ Cadastro de usuário com dados válidos
- ✅ Cadastro com e-mail duplicado (deve lançar exceção)
- ✅ Listagem de todos os usuários
- ✅ Exclusão de usuário existente
- ✅ Exclusão de usuário inexistente (deve lançar exceção)
- ✅ Teste de carregamento do contexto Spring

## 🏗️ Arquitetura e Padrões

### Monolito Modular
O projeto segue a arquitetura de **Monolito Modular**, onde cada domínio de negócio é isolado em seu próprio pacote, garantindo baixo acoplamento e alta coesão.

### Camadas
- **Controller:** Recebe requisições HTTP, valida dados de entrada e retorna respostas
- **Service:** Contém a lógica de negócio e regras de validação
- **Repository:** Camada de persistência com Spring Data JPA
- **Model/Entity:** Representação das tabelas do banco de dados
- **DTO:** Objetos de transferência de dados (Request/Response)

### Tratamento de Erros
Todas as exceções são tratadas centralizadamente pelo `GlobalExceptionHandler`, que retorna respostas padronizadas no formato `StandardError`:

```json
{
  "timestamp": "2026-06-18T10:00:00Z",
  "status": 400,
  "error": "Regra de Negócio Violada",
  "message": "E-mail já cadastrado no sistema.",
  "path": "/api/users"
}
```

### Segurança
- Senhas criptografadas com **BCrypt** antes de salvar no banco
- CSRF desabilitado (API REST)
- Headers configurados para permitir console H2

## 📋 Histórico de Commits

O projeto foi construído seguindo a metodologia **Conventional Commits** e a documentação presente na pasta `.cline/`. Abaixo o histórico completo de desenvolvimento:

| # | Commit | Descrição |
|---|--------|-----------|
| 1 | `chore: initial project structure` | Estrutura inicial do projeto Maven com Spring Boot |
| 2 | `build: add swagger, bcrypt, dotenv` | Configuração de dependências e `application.yml` |
| 3 | `feat: create User entity` | Entidade JPA `User` com UUID e Lombok |
| 4 | `feat: create UserRepository` | Interface Repository com método `findByEmail` |
| 5 | `feat: create DTOs` | Records `UserRegistrationDto`, `UserResponseDto`, `LoginRequestDto` |
| 6 | `feat: create UserService interface` | Interface e esqueleto do serviço |
| 7 | `feat: implement business logic` | Lógica de negócio com Stream API e BCrypt |
| 8 | `feat: implement UserController` | Endpoints REST com validação HTTP |
| 9 | `feat: implement GlobalExceptionHandler` | Tratamento global de exceções com Records |
| 10 | `fix: configure security filter chain` | Liberação de Swagger e H2 no Security |
| 11 | `fix: add @PathVariable` | Correção do método `alterar` no Controller |
| 12 | `test: implement unit tests` | Testes unitários com Mockito (5 cenários) |

## 📄 Licença

Este projeto é de uso educacional e livre para modificação.
