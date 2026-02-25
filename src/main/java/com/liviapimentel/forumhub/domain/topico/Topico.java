package com.liviapimentel.forumhub.domain.topico;

import com.liviapimentel.forumhub.domain.curso.Curso;
import com.liviapimentel.forumhub.domain.resposta.Resposta;
import com.liviapimentel.forumhub.domain.topico.dto.DadosRegistroTopico;
import com.liviapimentel.forumhub.domain.usuario.Usuario;
import com.liviapimentel.forumhub.infra.utils.FormatacaoTexto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@Entity(name = "Topico")
@Table(name = "topicos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private StatusTopico status = StatusTopico.NAO_RESPONDIDO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL)
    private List<Resposta> respostas = new ArrayList<>();

    public Topico(DadosRegistroTopico dados, Usuario autor, Curso curso) {
        this.titulo = FormatacaoTexto.formatarSentenca(dados.titulo());
        this.mensagem = dados.mensagem();
        this.dataCriacao = LocalDateTime.now();
        this.status = StatusTopico.NAO_RESPONDIDO;
        this.autor = autor;
        this.curso = curso;
    }
}
