package com.alison.sistemaanuncios.controllers;

import com.alison.sistemaanuncios.model.Categoria;
import com.alison.sistemaanuncios.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @RequestMapping(method = RequestMethod.GET, value = "categoria")
    public ModelAndView index() {
        ModelAndView model = new ModelAndView("categoria");
        model.addObject("catobj", new Categoria());
        return model;
    }

    @RequestMapping(method = RequestMethod.POST, value = "**/salvarcategoria")
    public ModelAndView salvarcategoria(@Valid Categoria categoria){

        ModelAndView model = new ModelAndView("/categoria");
        model.addObject("catobj", categoria);

        categoriaRepository.save(categoria);
        model.addObject("catobj", new Categoria());
        return model;
    }

}
