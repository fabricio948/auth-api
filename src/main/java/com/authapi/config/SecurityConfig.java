package com.authapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desabilita o CSRF porque estamos usando uma API REST imutável
                .csrf(csrf -> csrf.disable())
                // Define quais rotas estão liberadas ou protegidas
                .authorizeHttpRequests(auth -> auth
                        // Permite que qualquer pessoa acesse o Swagger e o Banco H2
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/h2-console/**").permitAll()
                        // Qualquer outra requisição precisará de autenticação (deixaremos liberado por enquanto para testar o CRUD)
                        .anyRequest().permitAll()
                )
                // Permite a visualização do console do H2 em frames nas páginas web
                .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}