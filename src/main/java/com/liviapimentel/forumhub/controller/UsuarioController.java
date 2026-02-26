package com.liviapimentel.forumhub.controller;

import com.liviapimentel.forumhub.domain.topico.dto.DadosAtualizarTopico;
import com.liviapimentel.forumhub.domain.topico.dto.DadosDetalhamentoTopico;
import com.liviapimentel.forumhub.domain.usuario.dto.DadosAtualizacaoUsuario;
import com.liviapimentel.forumhub.domain.usuario.dto.DadosCadastroUsuario;
import com.liviapimentel.forumhub.domain.usuario.Usuario;
import com.liviapimentel.forumhub.domain.usuario.UsuarioRepository;
import com.liviapimentel.forumhub.domain.usuario.dto.DadosDetalhamentoUsuario;
import com.liviapimentel.forumhub.domain.usuario.dto.DadosListagemUsuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public Page<DadosListagemUsuario> listar(@PageableDefault(size = 10, sort = {"nome"})  Pageable paginacao) {
        return repository.findAllAtivos(paginacao)
                .map(DadosListagemUsuario::new);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var usuarioOptional = repository.findById(id);

        if (usuarioOptional.isPresent()) {
            var usuario = usuarioOptional.get();

            // Se estiver inativo retorna um 404
            if(!usuario.getAtivo()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(new DadosDetalhamentoUsuario(usuario));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoUsuario> atualizar(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoUsuario dados) {
        var usuario = repository.getReferenceById(id);

        usuario.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoUsuario(usuario));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity exclui(@PathVariable Long id) {
        var usuario = repository.getReferenceById(id);
        usuario.excluir();

        return ResponseEntity.noContent().build();
    }
}
