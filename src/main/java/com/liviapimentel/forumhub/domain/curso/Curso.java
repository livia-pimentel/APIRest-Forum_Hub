package com.liviapimentel.forumhub.domain.curso;

import com.liviapimentel.forumhub.domain.curso.dto.DadosAtualizacaoCurso;
import com.liviapimentel.forumhub.domain.curso.dto.DadosCadastroCurso;
import com.liviapimentel.forumhub.infra.utils.FormatacaoTexto;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Curso")
@Table(name = "cursos")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String categoria;

    @Column(columnDefinition = "tinyint")
    private Boolean ativo = true;

    public Curso(DadosCadastroCurso dados) {
        this.nome = FormatacaoTexto.formatarSentenca(dados.nome());
        this.categoria = FormatacaoTexto.formatarSentenca(dados.categoria());
    }

    public void atualizarInformacoes(DadosAtualizacaoCurso dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.categoria() != null) {
            this.categoria = dados.categoria();
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}
