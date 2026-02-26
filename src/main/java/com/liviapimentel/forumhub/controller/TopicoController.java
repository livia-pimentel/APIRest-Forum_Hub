package com.liviapimentel.forumhub.controller;

import com.liviapimentel.forumhub.domain.curso.CursoRepository;
import com.liviapimentel.forumhub.domain.topico.dto.DadosListagemTopico;
import com.liviapimentel.forumhub.domain.topico.Topico;
import com.liviapimentel.forumhub.domain.topico.TopicoRepository;
import com.liviapimentel.forumhub.domain.topico.dto.DadosRegistroTopico;
import com.liviapimentel.forumhub.domain.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;


    @PostMapping
    @Transactional
    public void registrarTopico(@RequestBody @Valid DadosRegistroTopico dados) {
        var autor = usuarioRepository.findByNomeIgnoreCase(dados.autor().trim())
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));

        var curso = cursoRepository.findByNomeIgnoreCase(dados.curso().trim())
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        var topico = new Topico(dados, autor, curso);
        topicoRepository.save(topico);
    }

    @GetMapping()
    public Page<DadosListagemTopico> listar(@PageableDefault(
            size = 10,
            sort = {"curso.nome", "dataCriacao"},
            direction = Sort.Direction.ASC
    ) Pageable paginacao ) {
        return topicoRepository.findAll(paginacao)
                .map(DadosListagemTopico::new);
    }
}
