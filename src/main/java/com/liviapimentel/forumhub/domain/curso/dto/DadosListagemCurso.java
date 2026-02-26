package com.liviapimentel.forumhub.domain.curso.dto;

import com.liviapimentel.forumhub.domain.curso.Curso;

public record DadosListagemCurso(String nome, String categoria) {

    public DadosListagemCurso(Curso curso) {
        this(curso.getNome(), curso.getCategoria());
    }
}
