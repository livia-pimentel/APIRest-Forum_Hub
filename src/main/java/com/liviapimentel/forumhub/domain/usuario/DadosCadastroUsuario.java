package com.liviapimentel.forumhub.domain.usuario;

import java.util.Set;

public record DadosCadastroUsuario(
        String nome,
        String email,
        String senha,
        Set<Perfil> perfis
) {
}
