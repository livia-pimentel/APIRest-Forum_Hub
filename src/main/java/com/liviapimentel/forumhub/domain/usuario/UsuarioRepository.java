package com.liviapimentel.forumhub.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNomeIgnoreCase(String nome);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByNomeIgnoreCase(String nome);

    @Query("SELECT DISTINCT u FROM Usuario u LEFT JOIN FETCH u.perfis")
    List<Usuario> findAllComPerfis();
}
