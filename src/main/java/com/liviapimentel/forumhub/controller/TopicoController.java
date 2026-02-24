package com.liviapimentel.forumhub.controller;

import com.liviapimentel.forumhub.domain.curso.CursoRepository;
import com.liviapimentel.forumhub.domain.topico.Topico;
import com.liviapimentel.forumhub.domain.topico.TopicoRepository;
import com.liviapimentel.forumhub.domain.topico.dto.DadosRegistroTopico;
import com.liviapimentel.forumhub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void registrarTopico(@RequestBody DadosRegistroTopico dados) {
        var autor = usuarioRepository.findByNomeIgnoreCase(dados.autor().trim())
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));

        var curso = cursoRepository.findByNomeIgnoreCase(dados.curso().trim())
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        var topico = new Topico(dados, autor, curso);
        topicoRepository.save(topico);
    }
}
