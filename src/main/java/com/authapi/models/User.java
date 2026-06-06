package com.authapi.models;


import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;


@Entity
@Table(name = "tb_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false , unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;
}
