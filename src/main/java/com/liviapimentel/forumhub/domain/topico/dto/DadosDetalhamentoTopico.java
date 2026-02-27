package com.liviapimentel.forumhub.domain.topico.dto;

import com.liviapimentel.forumhub.domain.resposta.dto.DadosDetalhamentoResposta;
import com.liviapimentel.forumhub.domain.topico.StatusTopico;
import com.liviapimentel.forumhub.domain.topico.Topico;

import java.time.LocalDateTime;
import java.util.List;

public record DadosDetalhamentoTopico(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        String autor,
        String curso,
        StatusTopico statusTopico,
        List<DadosDetalhamentoResposta> respostas
) {
    public DadosDetalhamentoTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getAutor().getNome(),
                topico.getCurso().getNome(),
                topico.getStatus(),
                topico.getRespostas().stream()
                        .map(DadosDetalhamentoResposta::new)
                        .toList()
        );
    }
}
