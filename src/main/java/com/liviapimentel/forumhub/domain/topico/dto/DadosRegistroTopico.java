package com.liviapimentel.forumhub.domain.topico.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DadosRegistroTopico(
        String titulo,
        String mensagem,
        String autor,
        String  curso) {
}
