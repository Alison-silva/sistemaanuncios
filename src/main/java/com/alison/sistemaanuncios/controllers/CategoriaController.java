package com.alison.sistemaanuncios.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CategoriaController {


    @RequestMapping(method = RequestMethod.GET, value = "categoria")
    public String index() {


        return "categoria";
    }

}
