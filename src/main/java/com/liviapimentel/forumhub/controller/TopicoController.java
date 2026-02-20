package com.liviapimentel.forumhub.controller;

import com.liviapimentel.forumhub.domain.topico.dto.DadosRegistroTopico;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("topicos")
public class TopicoController {

    @PostMapping
    public void registrarTopico(@RequestBody DadosRegistroTopico dados) {
        System.out.println(dados);
    }
}
