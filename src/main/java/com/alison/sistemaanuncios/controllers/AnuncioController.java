package com.alison.sistemaanuncios.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AnuncioController {

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ModelAndView index() {
        ModelAndView model = new ModelAndView("index");

        return model;
    }

}
