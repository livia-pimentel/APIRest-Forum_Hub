package com.liviapimentel.forumhub.domain.curso;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    Optional<Curso> findByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCase(String nome);

    @Query("SELECT c FROM Curso c WHERE c.ativo = true")
    Page<Curso> findAllByAtivoTrue(Pageable paginacao);

    @Query("SELECT c FROM Curso c WHERE c.ativo = false")
    Page<Curso> findAllInativos(Pageable paginacao);

}
