package com.liviapimentel.forumhub.controller;

import com.liviapimentel.forumhub.domain.curso.CursoRepository;
import com.liviapimentel.forumhub.domain.topico.StatusTopico;
import com.liviapimentel.forumhub.domain.topico.dto.DadosAtualizarTopico;
import com.liviapimentel.forumhub.domain.topico.dto.DadosDetalhamentoTopico;
import com.liviapimentel.forumhub.domain.topico.dto.DadosListagemTopico;
import com.liviapimentel.forumhub.domain.topico.Topico;
import com.liviapimentel.forumhub.domain.topico.TopicoRepository;
import com.liviapimentel.forumhub.domain.topico.dto.DadosRegistroTopico;
import com.liviapimentel.forumhub.domain.usuario.UsuarioRepository;
import com.liviapimentel.forumhub.infra.exception.ValidacaoException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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
    public ResponseEntity registrarTopico(@RequestBody @Valid DadosRegistroTopico dados, UriComponentsBuilder uriBuilder) {

        var autor = usuarioRepository.findByNomeIgnoreCase(dados.autor().trim())
                .orElseThrow(() -> new ValidacaoException("Autor não encontrado"));

        var curso = cursoRepository.findByNomeIgnoreCase(dados.curso().trim())
                .orElseThrow(() -> new ValidacaoException("Curso não encontrado"));

        var topico = new Topico(dados, autor, curso);

        topicoRepository.save(topico);

        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));

    }

    @GetMapping()
    public ResponseEntity<Page<DadosListagemTopico>> listar(@PageableDefault(
            size = 10,
            sort = {"curso.nome", "dataCriacao"},
            direction = Sort.Direction.ASC
    ) Pageable paginacao ) {

        var page =  topicoRepository.findAllAtivos(paginacao)
                .map(DadosListagemTopico::new);
        return ResponseEntity.ok(page);

    }

    @GetMapping("/arquivados")
    public ResponseEntity<Page<DadosListagemTopico>>listarArquivados(Pageable paginacao) {

        var page = topicoRepository.findAllByStatus(StatusTopico.FECHADO, paginacao)
                .map(DadosListagemTopico::new);
        return ResponseEntity.ok(page);

    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var topico = topicoRepository.getReferenceById(id);

        if (topico.getStatus() == StatusTopico.FECHADO) {
            throw new EntityNotFoundException();
        }

        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));

    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoTopico> atualizar(@PathVariable Long id, @RequestBody @Valid DadosAtualizarTopico dados) {
        var topico = topicoRepository.getReferenceById(id);

        if (topico.getStatus() == StatusTopico.FECHADO) {
            throw new EntityNotFoundException();
        }

        topico.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {

        var topico = topicoRepository.getReferenceById(id);
        topico.excluir();
        return ResponseEntity.noContent().build();

    }
}
