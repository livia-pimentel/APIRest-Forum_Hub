package com.liviapimentel.forumhub.domain.resposta.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroResposta(
        @NotBlank
        String mensagem,
        @NotNull
        Long idAutor,
        @NotNull
        Long idTopico
) {
}
