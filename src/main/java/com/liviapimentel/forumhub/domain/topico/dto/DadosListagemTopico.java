package com.liviapimentel.forumhub.domain.topico.dto;

import com.liviapimentel.forumhub.domain.topico.StatusTopico;
import com.liviapimentel.forumhub.domain.topico.Topico;

import java.time.LocalDateTime;

public record DadosListagemTopico(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        StatusTopico status,
        String autor,
        String curso
) {

    public DadosListagemTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao(), topico.getStatus(), topico.getAutor().getNome(), topico.getCurso().getNome());
    }
}
