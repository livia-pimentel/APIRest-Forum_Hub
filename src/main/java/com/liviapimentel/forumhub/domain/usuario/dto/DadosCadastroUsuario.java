package com.liviapimentel.forumhub.domain.usuario.dto;

import com.liviapimentel.forumhub.domain.usuario.Perfil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record DadosCadastroUsuario(
        @NotBlank
        String nome,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String senha,
        @NotEmpty(message = "O usuário dever ter um perfil")
        Set<Perfil> perfis
) {
}
