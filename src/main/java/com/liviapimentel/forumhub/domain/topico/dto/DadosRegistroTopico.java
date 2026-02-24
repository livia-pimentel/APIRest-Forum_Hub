package com.liviapimentel.forumhub.domain.topico.dto;

public record DadosRegistroTopico(
        String titulo,
        String mensagem,
        Long idAutor,
        Long idCurso) {
}
