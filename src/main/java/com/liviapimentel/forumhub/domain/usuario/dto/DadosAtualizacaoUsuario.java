package com.liviapimentel.forumhub.domain.usuario.dto;

import com.liviapimentel.forumhub.domain.usuario.Perfil;

import java.util.Set;

public record DadosAtualizacaoUsuario(String nome,
                                      String email,
                                      Set<Perfil> perfis) {
}
