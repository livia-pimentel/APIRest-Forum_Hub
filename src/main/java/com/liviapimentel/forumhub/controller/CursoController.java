package com.liviapimentel.forumhub.controller;

import com.liviapimentel.forumhub.domain.curso.Curso;
import com.liviapimentel.forumhub.domain.curso.CursoRepository;
import com.liviapimentel.forumhub.domain.curso.dto.DadosCadastroCurso;
import com.liviapimentel.forumhub.domain.curso.dto.DadosListagemCurso;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<DadosListagemCurso> listar(Pageable paginacao) {
        return repository.findAll(paginacao)
                .map(DadosListagemCurso::new);
    }

}
