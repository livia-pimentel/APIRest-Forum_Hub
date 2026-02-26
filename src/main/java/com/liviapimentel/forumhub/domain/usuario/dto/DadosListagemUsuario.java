package com.liviapimentel.forumhub.domain.usuario.dto;

import com.liviapimentel.forumhub.domain.usuario.Usuario;

import java.util.stream.Collectors;

public record DadosListagemUsuario(String nome, String email, String perfil) {
    public DadosListagemUsuario(Usuario usuario) {
        this(usuario.getNome(),
                usuario.getEmail(),
                usuario.getPerfis().stream()
                        .map(Enum::name)
                        .collect(Collectors.joining(", ")));
    }
}
