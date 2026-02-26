package com.liviapimentel.forumhub.controller;

import com.liviapimentel.forumhub.domain.resposta.Resposta;
import com.liviapimentel.forumhub.domain.resposta.RespostaRepository;
import com.liviapimentel.forumhub.domain.resposta.dto.DadosCadastroResposta;
import com.liviapimentel.forumhub.domain.resposta.dto.DadosDetalhamentoResposta;
import com.liviapimentel.forumhub.domain.topico.StatusTopico;
import com.liviapimentel.forumhub.domain.topico.TopicoRepository;
import com.liviapimentel.forumhub.domain.usuario.UsuarioRepository;
import com.liviapimentel.forumhub.infra.ValidacaoException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("respostas")
public class RespostaController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TopicoRepository topicoRepository;

    @Autowired
    RespostaRepository respostaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity registrar(@RequestBody @Valid DadosCadastroResposta dados, UriComponentsBuilder uriBuilder) {

        var autor = usuarioRepository.getReferenceById(dados.idAutor());
        if (!autor.getAtivo()) {
            throw new EntityNotFoundException();
        }

        var topico = topicoRepository.getReferenceById(dados.idTopico());
        if (topico.getStatus() == StatusTopico.FECHADO) {
            throw new ValidacaoException("Não é possível responder a um tópico fechado.");
        }

        var resposta = new Resposta(dados, autor, topico);
        respostaRepository.save(resposta);

        var uri = uriBuilder.path("/respostas/{id}").buildAndExpand(resposta.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoResposta(resposta));
    }
}
