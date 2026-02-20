package com.liviapimentel.forumhub.domain.resposta;

import com.liviapimentel.forumhub.domain.usuario.Usuario;

import java.time.LocalDateTime;

public class Resposta {

    private Long id;
    private String mensagem;
    private LocalDateTime dataCriacao = LocalDateTime.now();
    private Boolean solucao = false;
//    private Topico topico;
    private Usuario autor;
}
