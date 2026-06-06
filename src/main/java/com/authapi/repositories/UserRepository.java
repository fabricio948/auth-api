package com.authapi.repositories;


import com.authapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User , UUID> {

    // Método customizado para encontrar um usuário pelo email
    Optional<User> findByEmail(String email);
}
