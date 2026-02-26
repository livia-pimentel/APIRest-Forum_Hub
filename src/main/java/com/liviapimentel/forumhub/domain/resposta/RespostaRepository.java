package com.liviapimentel.forumhub.domain.resposta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {
    List<Resposta> findAllByTopicoId(Long topicoId);
}
