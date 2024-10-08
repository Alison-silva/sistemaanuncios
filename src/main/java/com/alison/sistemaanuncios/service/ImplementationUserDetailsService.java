package com.alison.sistemaanuncios.service;

import com.alison.sistemaanuncios.model.Usuario;
import com.alison.sistemaanuncios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ImplementationUserDetailsService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findUserByLogin(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não foi encontrado");
        }
        return new User(usuario.getLogin(), usuario.getSenha(), usuario.getAuthorities());
    }

    public void insereAcessoPadrao(Long id) {
        String constraint = usuarioRepository.consultaConstraintRole();
        if(constraint != null) {
            jdbcTemplate.execute(" alter table usuarios_role drop constraint " + constraint);
        }
        usuarioRepository.insereAcessoRolePadrao(id);
    }

    public void insereAcessoAdmin(Long id) {
        String constraint = usuarioRepository.consultaConstraintRole();
        if (constraint != null) {
            jdbcTemplate.execute("ALTER TABLE usuarios_role DROP CONSTRAINT " + constraint);
        }
        usuarioRepository.insereAcessoRoleAdmin(id);
    }



}
