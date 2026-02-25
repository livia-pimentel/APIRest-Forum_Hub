package com.liviapimentel.forumhub.controller;

import com.liviapimentel.forumhub.domain.usuario.DadosCadastroUsuario;
import com.liviapimentel.forumhub.domain.usuario.Usuario;
import com.liviapimentel.forumhub.domain.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroUsuario dados) {

        if (repository.existsByEmailIgnoreCase(dados.email())) {
            throw new RuntimeException("Este e-mail já está cadastrado.");
        }

        if (repository.existsByNomeIgnoreCase(dados.nome())) {
            throw new RuntimeException("Já existe usuário cadastrado com esse nome.");
        }
        repository.save(new Usuario(dados));
    }
}
