package com.liviapimentel.forumhub.domain.usuario.dto;

import com.liviapimentel.forumhub.domain.usuario.Perfil;
import com.liviapimentel.forumhub.domain.usuario.Usuario;

import java.util.Set;

public record DadosDetalhamentoUsuario(
        Long id,
        String nome,
        String email,
        Set<Perfil> perfis

) {
    public DadosDetalhamentoUsuario(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPerfis()
        );
    }
}
