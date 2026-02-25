package com.liviapimentel.forumhub.controller;

import com.liviapimentel.forumhub.domain.curso.Curso;
import com.liviapimentel.forumhub.domain.curso.CursoRepository;
import com.liviapimentel.forumhub.domain.curso.DadosCadastroCurso;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cursos")
public class CursoController {

    @Autowired
    private CursoRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroCurso dados) {
        repository.save(new Curso(dados));
    }
}
