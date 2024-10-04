package com.alison.sistemaanuncios.model;

import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@SequenceGenerator(name = "seq_usuario", sequenceName = "seq_usuario", allocationSize = 1, initialValue = 1)
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
    private Long id;

    @NotEmpty(message = "O nome não pode ser vazio!")
    @NotNull(message = "O nome não pode ser nulo!")
    private String nome;

    @NotEmpty(message = "O telefone não pode ser vazio!")
    @NotNull(message = "O telefone não pode ser nulo!")
    private String telefone;

    @NotEmpty(message = "O e-mail não pode ser vazio!")
    @NotNull(message = "O e-mail não pode ser nulo!")
    private String email;

    @NotEmpty(message = "O login não pode ser vazio!")
    @NotNull(message = "O login não pode ser nulo!")
    private String login;

    @NotEmpty(message = "A senha não pode ser vazia!")
    @NotNull(message = "A senha não pode ser nula!")
    private String senha;

    @OneToMany(mappedBy = "usuario", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Anuncio> anuncios = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuarios_role", uniqueConstraints = @UniqueConstraint(columnNames = { "usuario_id",
            "role_id" }, name = "unique_role_user"), joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id", table = "usuario", unique = false, foreignKey = @ForeignKey(name = "usuario_fk", value = ConstraintMode.CONSTRAINT)), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id", table = "role", unique = false, foreignKey = @ForeignKey(name = "role_fk", value = ConstraintMode.CONSTRAINT)))
    private List<Role> roles = new ArrayList<Role>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Anuncio> getAnuncios() {
        return anuncios;
    }

    public void setAnuncios(List<Anuncio> anuncios) {
        this.anuncios = anuncios;
    }

    @Override
    public Collection<Role> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
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
        return true;
    }
}
