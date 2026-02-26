package com.liviapimentel.forumhub.domain.usuario;

import com.liviapimentel.forumhub.domain.topico.dto.DadosAtualizarTopico;
import com.liviapimentel.forumhub.domain.usuario.dto.DadosAtualizacaoUsuario;
import com.liviapimentel.forumhub.domain.usuario.dto.DadosCadastroUsuario;
import com.liviapimentel.forumhub.infra.utils.FormatacaoTexto;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String senha;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "usuario_perfis", joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "perfil")
    @Enumerated(EnumType.STRING)
    private Set<Perfil> perfis = new HashSet<>();

    public Usuario(DadosCadastroUsuario dados) {
        this.nome = FormatacaoTexto.formatarNomeProprio(dados.nome());
        this.email = dados.email();
        this.senha = dados.senha();
        this.perfis = dados.perfis();
    }

    public void atualizarInformacoes(@Valid DadosAtualizacaoUsuario dados) {
        if (dados.nome() != null) {
            this.nome = FormatacaoTexto.formatarNomeProprio(dados.nome());
        }

        if (dados.email() != null) {
            this.email = dados.email();
        }

        if (dados.perfis() != null && !dados.perfis().isEmpty()) {
            this.perfis = dados.perfis();
        }
    }
}
