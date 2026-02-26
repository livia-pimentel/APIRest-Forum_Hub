package com.liviapimentel.forumhub.domain.curso.dto;

import com.liviapimentel.forumhub.domain.curso.Curso;

public record DadosDetalhamentoCurso(
        Long id,
        String nome,
        String categoria,
        Boolean ativo
) {
    public DadosDetalhamentoCurso(Curso curso) {
        this(
                curso.getId(),
                curso.getNome(),
                curso.getCategoria(),
                curso.getAtivo()
        );
    }
}
