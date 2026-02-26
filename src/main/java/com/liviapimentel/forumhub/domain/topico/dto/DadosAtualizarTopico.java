package com.liviapimentel.forumhub.domain.topico.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosAtualizarTopico(
        @NotBlank
        String titulo,
        @NotBlank
        String mensagem
) {
}
