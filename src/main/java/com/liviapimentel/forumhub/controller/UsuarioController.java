package com.liviapimentel.forumhub.controller;

import com.liviapimentel.forumhub.domain.usuario.dto.DadosAtualizacaoUsuario;
import com.liviapimentel.forumhub.domain.usuario.dto.DadosCadastroUsuario;
import com.liviapimentel.forumhub.domain.usuario.Usuario;
import com.liviapimentel.forumhub.domain.usuario.UsuarioRepository;
import com.liviapimentel.forumhub.domain.usuario.dto.DadosDetalhamentoUsuario;
import com.liviapimentel.forumhub.domain.usuario.dto.DadosListagemUsuario;
import com.liviapimentel.forumhub.infra.ValidacaoException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroUsuario dados, UriComponentsBuilder uriBuilder) {
        if (repository.existsByEmailIgnoreCase(dados.email())) {
            throw new ValidacaoException("Este e-mail já está cadastrado.");
        }
        if (repository.existsByNomeIgnoreCase(dados.nome())) {
            throw new ValidacaoException("Já existe usuário cadastrado com esse nome.");
        }

        var usuario = new Usuario(dados);
        repository.save(usuario);

        var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoUsuario(usuario));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemUsuario>> listar(@PageableDefault(size = 10, sort = {"nome"})  Pageable paginacao) {
        var page = repository.findAllAtivos(paginacao)
                .map(DadosListagemUsuario::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var usuario = repository.getReferenceById(id);

        if(!usuario.getAtivo()) {
            throw new EntityNotFoundException();
        }

        return ResponseEntity.ok(new DadosDetalhamentoUsuario(usuario));
    }

    @GetMapping("/inativos")
    public ResponseEntity<Page<DadosListagemUsuario>> listarInativos(@PageableDefault(size = 10, sort = {"id"}) Pageable paginacao) {
        var page =  repository.findAllInativos(paginacao)
                .map(DadosListagemUsuario::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoUsuario> atualizar(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoUsuario dados) {
        var usuario = repository.getReferenceById(id);

        if (!usuario.getAtivo()) {
            throw new EntityNotFoundException();
        }

        usuario.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoUsuario(usuario));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {

        var usuario = repository.getReferenceById(id);

        if (!usuario.getAtivo()) {
            throw new EntityNotFoundException();
        }

        usuario.excluir();
        return ResponseEntity.noContent().build();
    }
}
