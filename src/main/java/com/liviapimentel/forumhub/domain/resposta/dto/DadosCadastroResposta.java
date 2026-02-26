package com.liviapimentel.forumhub.domain.resposta.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroResposta(
        @NotBlank
        String mensagem,
        @NotBlank
        Long idAutor,
        @NotBlank
        Long idTopico
) {
}
