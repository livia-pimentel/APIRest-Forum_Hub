package com.liviapimentel.forumhub.domain.topico.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;

public record DadosAtualizarTopico(
        String titulo,
        String mensagem
) {
        @AssertTrue(message = "Pelo menos um dos campos (titulo ou mensagem) deve ser preenchido para atualização")
        public boolean isUmCampoPreenchido() {
                return (titulo != null && !titulo.isBlank()) || (mensagem != null && !mensagem.isBlank());
        }
}
