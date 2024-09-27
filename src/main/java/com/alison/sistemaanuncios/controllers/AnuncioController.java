package com.alison.sistemaanuncios.controllers;

import com.alison.sistemaanuncios.model.Usuario;
import com.alison.sistemaanuncios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AnuncioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    Usuario usuario = new Usuario();

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ModelAndView index() {
        ModelAndView model = new ModelAndView("index");
        buscarUsuarioLogado();
        model.addObject("usuario", usuario);
        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "anuncio")
    public ModelAndView anuncio() {
        ModelAndView model = new ModelAndView("anuncio");

        return model;
    }

    private void buscarUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String login = authentication.getName();
            usuario = usuarioRepository.buscarUsuarioLogin(login).get(0);
        }

    }

}
