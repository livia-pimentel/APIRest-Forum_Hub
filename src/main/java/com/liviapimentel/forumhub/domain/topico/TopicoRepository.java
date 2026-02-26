package com.liviapimentel.forumhub.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    @Query("""
            SELECT t FROM Topico t
            JOIN FETCH t.autor
            JOIN FETCH t.curso
            WHERE t.status != 'FECHADO'
            """)
    Page<Topico> findAllAtivos(Pageable paginacao);

    @Query("SELECT t FROM Topico t WHERE t.id = :id AND t.status != 'FECHADO'")
    Optional<Topico> findByIdAtivo(Long id);

    Page<Topico> findAllByStatus(StatusTopico status, Pageable paginacao);
}
