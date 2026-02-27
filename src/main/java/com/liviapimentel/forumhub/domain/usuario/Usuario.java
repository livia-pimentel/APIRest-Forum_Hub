package com.liviapimentel.forumhub.domain.usuario;

import com.liviapimentel.forumhub.domain.topico.dto.DadosAtualizarTopico;
import com.liviapimentel.forumhub.domain.usuario.dto.DadosAtualizacaoUsuario;
import com.liviapimentel.forumhub.domain.usuario.dto.DadosCadastroUsuario;
import com.liviapimentel.forumhub.infra.utils.FormatacaoTexto;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario  implements UserDetails {

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

    @Column(columnDefinition = "tinyint")
    private Boolean ativo = true;

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

    public void excluir() {
        this.ativo = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Converte os Perfis em SimpleGrantedAuthority
        return this.perfis.stream()
                .map(p -> new SimpleGrantedAuthority("ROLE_" + p.name()))
                .toList();
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return ativo; // Se o usuário for inativado , o login é bloqueado
    }
}
