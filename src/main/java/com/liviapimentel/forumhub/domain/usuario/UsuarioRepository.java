package com.liviapimentel.forumhub.domain.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNomeIgnoreCase(String nome);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByNomeIgnoreCase(String nome);


    @Query(value = "SELECT DISTINCT u FROM Usuario u LEFT JOIN FETCH u.perfis WHERE u.ativo = true",
            countQuery = "SELECT COUNT(DISTINCT u) FROM Usuario u WHERE u.ativo = true")
    Page<Usuario> findAllAtivos(Pageable paginacao);

    @Query(value = "SELECT DISTINCT u FROM Usuario u LEFT JOIN FETCH u.perfis WHERE u.ativo = false",
            countQuery = "SELECT COUNT(DISTINCT u) FROM Usuario u WHERE u.ativo = false")
    Page<Usuario> findAllInativos(Pageable paginacao);

    UserDetails findByEmail(String email);
}
