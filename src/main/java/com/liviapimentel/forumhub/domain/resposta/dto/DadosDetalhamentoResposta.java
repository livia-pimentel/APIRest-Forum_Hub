package com.liviapimentel.forumhub.domain.resposta.dto;

import com.liviapimentel.forumhub.domain.resposta.Resposta;

import java.time.LocalDateTime;

public record DadosDetalhamentoResposta(
        Long id,
        String mensagem,
        LocalDateTime dataDaResposta,
        Boolean solucao,
        String autor,
        Long idTopico
) {

    public DadosDetalhamentoResposta(Resposta resposta) {
        this(
                resposta.getId(),
                resposta.getMensagem(),
                resposta.getDataCriacao(),
                resposta.getSolucao(),
                resposta.getAutor().getNome(),
                resposta.getTopico().getId()
        );
    }
}
