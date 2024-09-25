package com.alison.sistemaanuncios.controllers;

import com.alison.sistemaanuncios.model.Categoria;
import com.alison.sistemaanuncios.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @RequestMapping(method = RequestMethod.GET, value = "categoria")
    public ModelAndView index() {
        ModelAndView model = new ModelAndView("categoria");
        model.addObject("categorias", categoriaRepository.findAll(PageRequest.of(0, 5, Sort.by("id"))));
        model.addObject("catobj", new Categoria());
        return model;
    }

    @GetMapping("/categoriapag")
    public ModelAndView carregaCatPorPaginacao(@PageableDefault(size=5, sort = {"id"}) Pageable pageable,
                                                  ModelAndView model) {
        Page<Categoria> pageCategoria = categoriaRepository.findAll(pageable);
        model.addObject("categorias", pageCategoria);
        model.addObject("catobj", new Categoria());
        model.setViewName("categoria");
        return model;
    }

    @RequestMapping(method = RequestMethod.POST, value = "**/salvarcategoria")
    public ModelAndView salvarcategoria(@Valid Categoria categoria){
        ModelAndView model = new ModelAndView("/categoria");
        model.addObject("catobj", categoria);
        categoriaRepository.save(categoria);
        model.addObject("categorias", categoriaRepository.findAll(PageRequest.of(0, 5, Sort.by("id"))));
        model.addObject("catobj", new Categoria());
        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "**/editcategoria/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        ModelAndView model = new ModelAndView("categoria");
        model.addObject("categorias", categoriaRepository.findAll(PageRequest.of(0, 5, Sort.by("id"))));
        model.addObject("catobj", categoria);
        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/deletecategoria/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, Categoria categoria) {
        categoriaRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("categorias", categoriaRepository.findAll(PageRequest.of(0, 5, Sort.by("id"))));
        redirectAttributes.addFlashAttribute("catobj", categoria);
        return "redirect:/categoria";
    }

}
