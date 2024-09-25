package com.alison.sistemaanuncios.controllers;

import com.alison.sistemaanuncios.model.Usuario;
import com.alison.sistemaanuncios.repositories.UsuarioRepository;
import com.alison.sistemaanuncios.service.ImplementationUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ImplementationUserDetailsService implementationUserDetailsService;

    @RequestMapping(method = RequestMethod.GET, value = "**/registrar")
    public ModelAndView registro() {
        ModelAndView model = new ModelAndView("registro");
        return model;
    }

    @RequestMapping(method = RequestMethod.POST, value = "**/salvausuario")
    public ModelAndView salvausuario(@Valid Usuario usuario, BindingResult bindingResult) throws Exception {
        ModelAndView model = new ModelAndView("login");
        String msgok = new String("Registrado com sucesso!");
        String senhacriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
        usuario.setSenha(senhacriptografada);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        implementationUserDetailsService.insereAcessoPadrao(usuarioSalvo.getId());
        model.addObject("msgok", msgok);
        return model;
    }


    @RequestMapping(method = RequestMethod.GET, value = "**/perfil")
    public ModelAndView perfil() {
        ModelAndView model = new ModelAndView("perfil");
        return model;
    }


}
