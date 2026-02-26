package com.liviapimentel.forumhub.domain.curso.dto;

import com.liviapimentel.forumhub.domain.curso.Curso;

public record DadosListagemCurso(Long id, String nome, String categoria) {

    public DadosListagemCurso(Curso curso) {
        this(curso.getId(), curso.getNome(), curso.getCategoria());
    }
}
