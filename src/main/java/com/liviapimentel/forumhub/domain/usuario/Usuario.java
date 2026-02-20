package com.liviapimentel.forumhub.domain.usuario;


import java.util.HashSet;
import java.util.Set;

public class Usuario {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private Set<Perfil> perfis = new HashSet<>();
}
