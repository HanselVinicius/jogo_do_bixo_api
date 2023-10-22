package com.vinicius.br.jogo_do_bixo.models.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    UserDetails findByLogin(String login);


    @Query(value = "SELECT * FROM users u WHERE u.login = :login",
            nativeQuery = true)
    Usuario findUserByLogin(String login);
}
