package com.liviapimentel.forumhub.controller;

import com.liviapimentel.forumhub.domain.curso.Curso;
import com.liviapimentel.forumhub.domain.curso.CursoRepository;
import com.liviapimentel.forumhub.domain.curso.dto.DadosAtualizacaoCurso;
import com.liviapimentel.forumhub.domain.curso.dto.DadosCadastroCurso;
import com.liviapimentel.forumhub.domain.curso.dto.DadosDetalhamentoCurso;
import com.liviapimentel.forumhub.domain.curso.dto.DadosListagemCurso;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cursos")
public class CursoController {

    @Autowired
    private CursoRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroCurso dados) {
        if (repository.existsByNomeIgnoreCase(dados.nome())) {
            throw new RuntimeException("Já existe um curso cadastrado com este nome!");
        }

        repository.save(new Curso(dados));
    }

    @GetMapping
    public Page<DadosListagemCurso> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao)
                .map(DadosListagemCurso::new);
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
    public Page<DadosDetalhamentoCurso> listarInativos(@PageableDefault(size = 10, sort = {"id"}) Pageable paginacao) {
        return repository.findAllInativos(paginacao)
                .map(DadosDetalhamentoCurso::new);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoCurso dados) {
        var curso = repository.getReferenceById(id);
        curso.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoCurso(curso));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var curso = repository.getReferenceById(id);
        curso.excluir();
        return ResponseEntity.noContent().build();
    }
}
