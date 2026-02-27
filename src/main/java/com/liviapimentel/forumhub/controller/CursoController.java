package com.liviapimentel.forumhub.controller;

import com.liviapimentel.forumhub.domain.curso.Curso;
import com.liviapimentel.forumhub.domain.curso.CursoRepository;
import com.liviapimentel.forumhub.domain.curso.dto.DadosAtualizacaoCurso;
import com.liviapimentel.forumhub.domain.curso.dto.DadosCadastroCurso;
import com.liviapimentel.forumhub.domain.curso.dto.DadosDetalhamentoCurso;
import com.liviapimentel.forumhub.domain.curso.dto.DadosListagemCurso;
import com.liviapimentel.forumhub.infra.exception.ValidacaoException;
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
@RequestMapping("cursos")
public class CursoController {

    @Autowired
    private CursoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroCurso dados, UriComponentsBuilder uriBuilder) {
        if (repository.existsByNomeIgnoreCase(dados.nome())) {
            throw new ValidacaoException("Já existe um curso cadastrado com este nome!");
        }

        var curso = new Curso(dados);
        repository.save(curso);

        var uri = uriBuilder.path("/cursos/{id}").buildAndExpand(curso.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoCurso(curso));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemCurso>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {

        var page = repository.findAllByAtivoTrue(paginacao)
                .map(DadosListagemCurso::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var curso = repository.getReferenceById(id);

        if (!curso.getAtivo()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new DadosDetalhamentoCurso(curso));
    }

    @GetMapping("/inativos")
    public ResponseEntity<Page<DadosDetalhamentoCurso>> listarInativos(@PageableDefault(size = 10, sort = {"id"}) Pageable paginacao) {

        var page = repository.findAllInativos(paginacao)
                .map(DadosDetalhamentoCurso::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoCurso dados) {

        var curso = repository.getReferenceById(id);

        if (!curso.getAtivo()) {
            throw new EntityNotFoundException();
        }

        curso.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoCurso(curso));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {

        var curso = repository.getReferenceById(id);

        if (!curso.getAtivo()) {
            throw new EntityNotFoundException();
        }

        curso.excluir();
        return ResponseEntity.noContent().build();

    }
}
