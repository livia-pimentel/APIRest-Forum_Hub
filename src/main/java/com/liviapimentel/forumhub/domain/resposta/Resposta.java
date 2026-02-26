package com.liviapimentel.forumhub.domain.resposta;

import com.liviapimentel.forumhub.domain.resposta.dto.DadosCadastroResposta;
import com.liviapimentel.forumhub.domain.topico.Topico;
import com.liviapimentel.forumhub.domain.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "Resposta")
@Table(name = "respostas")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensagem;
    private LocalDateTime dataCriacao = LocalDateTime.now();
    private Boolean solucao = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    private Topico topico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    public Resposta(@Valid DadosCadastroResposta dados, Usuario autor, Topico topico) {
        this.mensagem = dados.mensagem();
        this.autor = autor;
        this.topico = topico;
        this.dataCriacao = LocalDateTime.now();
        this.solucao = false;
    }
}
