package com.alison.sistemaanuncios.controllers;

import com.alison.sistemaanuncios.model.Anuncio;
import com.alison.sistemaanuncios.model.Categoria;
import com.alison.sistemaanuncios.model.Usuario;
import com.alison.sistemaanuncios.repositories.AnuncioRepository;
import com.alison.sistemaanuncios.repositories.CategoriaRepository;
import com.alison.sistemaanuncios.repositories.UsuarioRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Optional;

@Controller
public class AnuncioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private AnuncioRepository anuncioRepository;


    Usuario usuario = new Usuario();

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ModelAndView index() {
        ModelAndView model = new ModelAndView("index");
        buscarUsuarioLogado();
        model.addObject("categorias", categoriaRepository.findAll());
        model.addObject("anuncios", anuncioRepository.findAll(PageRequest.of(0, 8, Sort.by("id"))));
        model.addObject("usuario", usuario);
        return model;
    }

    @GetMapping("**/anuncioindexpag")
    public ModelAndView carregaAnunPorPaginacaoIndex(@PageableDefault(size = 8, sort = { "id" }) Pageable pageable,
                                                     @RequestParam(value = "titulopesquisa", required = false) String titulopesquisa,
                                                     @RequestParam(value = "categoriapesquisa", required = false) Long id,
                                                     ModelAndView model) {
        Page<Anuncio> pageAnuncio;
        if (id != null) {
            Categoria categoria = categoriaRepository.findById(id).orElse(null);
            pageAnuncio = anuncioRepository.findAnuncioByCategoriaPage(categoria, pageable);
        } else {
            pageAnuncio = anuncioRepository.findAnuncioByTituloPage(titulopesquisa, pageable);
        }

        model.addObject("categorias", categoriaRepository.findAll());
        model.addObject("anuncios", pageAnuncio);
        model.addObject("titulopesquisa", titulopesquisa);
        model.addObject("categoriapesquisa", id);
        buscarUsuarioLogado();
        model.addObject("usuario", usuario);
        model.setViewName("index");
        return model;
    }

    @PostMapping("**/buscatitulo")
    public ModelAndView buscatitulo(@RequestParam(value = "titulopesquisa", required = false) String titulopesquisa,
                                    @PageableDefault(size = 8, sort = { "id" }) Pageable pageable) {
        Page<Anuncio> anuncios = anuncioRepository.findAnuncioByTituloPage(titulopesquisa, pageable);
        ModelAndView model = new ModelAndView("index");
        model.addObject("categorias", categoriaRepository.findAll());
        model.addObject("anuncios", anuncios);
        model.addObject("titulopesquisa", titulopesquisa);
        buscarUsuarioLogado();
        model.addObject("usuario", usuario);
        return model;
    }

    @PostMapping("**/buscacategoria")
    public ModelAndView buscacategoria(@RequestParam(value = "categoriapesquisa", required = false) Long id,
                                       @PageableDefault(size = 8, sort = { "id" }) Pageable pageable) {
        ModelAndView model = new ModelAndView("index");
        Categoria categoria = null;
        if (id != null) {
            categoria = categoriaRepository.findById(id).orElse(null);
        }
        Page<Anuncio> anuncios = anuncioRepository.findAnuncioByCategoriaPage(categoria, pageable);
        model.addObject("categorias", categoriaRepository.findAll());
        model.addObject("anuncios", anuncios);
        model.addObject("categoriapesquisa", id);
        buscarUsuarioLogado();
        model.addObject("usuario", usuario);
        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "**/anuncio")
    public ModelAndView anuncio() {

        ModelAndView model = new ModelAndView("anuncio");
        buscarUsuarioLogado();
        model.addObject("categorias", categoriaRepository.findAll());
        model.addObject("usuario", usuario);
        model.addObject("anuncios", anuncioRepository.findAnuncioByUserId(usuario.getId(), PageRequest.of(0, 5, Sort.by("id"))));
        model.addObject("anuncioobj", new Anuncio());
        return model;
    }

    @GetMapping("/anunciopag")
    public ModelAndView carregaAnunPorPaginacao(@PageableDefault(size=5, sort = {"id"}) Pageable pageable,
                                               ModelAndView model) {
        Page<Anuncio> pageAnuncio = anuncioRepository.findAnuncioByUserId(usuario.getId(), pageable);
        model.addObject("anuncios", pageAnuncio);
        model.addObject("anuncioobj", new Anuncio());
        model.addObject("categorias", categoriaRepository.findAll());
        model.setViewName("anuncio");
        buscarUsuarioLogado();
        model.addObject("usuario", usuario);
        return model;
    }

    @RequestMapping(method = RequestMethod.POST, value = "**/salvaranuncio", consumes = { "multipart/form-data" })
    public String salvaranuncio(@Valid Anuncio anuncio, BindingResult bindingResult, final MultipartFile file, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute("anuncioobj", anuncio);
        if (file.getSize() > 0) {
            anuncio.setImage(file.getBytes());
        } else {
            if (anuncio.getId() != null && anuncio.getId() > 0) {
                byte[] imageTemp = anuncioRepository.findById(anuncio.getId()).get().getImage();
                anuncio.setImage(imageTemp);
            }
        }
        Date now = new Date();
        anuncio.setTimestamp(now);
        redirectAttributes.addFlashAttribute("usuario", usuario);
        anuncio.setUsuario(usuario);
        String msgok = new String("Registrado com sucesso!");
        anuncioRepository.save(anuncio);
        redirectAttributes.addFlashAttribute("msgok", msgok);
        redirectAttributes.addFlashAttribute("anuncioobj", new Anuncio());
        redirectAttributes.addFlashAttribute("anuncios", anuncioRepository.findAnuncioByUserId(usuario.getId(), PageRequest.of(0, 5, Sort.by("id"))));
        return "redirect:/anuncio";
    }

    @GetMapping("/imagemanuncio/{idanun}")
    public void imagemanuncio(@PathVariable("idanun") Long idanun, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");
        Anuncio anuncio = anuncioRepository.findById(idanun).get();
        InputStream is = new ByteArrayInputStream(anuncio.getImage());
        IOUtils.copy(is, response.getOutputStream());
    }

    @RequestMapping(method = RequestMethod.GET, value = "**/editanuncio/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Anuncio> anuncio = anuncioRepository.findById(id);
        ModelAndView model = new ModelAndView("anuncio");
        model.addObject("anuncioobj", anuncio);
        model.addObject("anuncios", anuncioRepository.findAnuncioByUserId(usuario.getId(), PageRequest.of(0, 5, Sort.by("id"))));
        model.addObject("categorias", categoriaRepository.findAll());
        buscarUsuarioLogado();
        model.addObject("usuario", usuario);
        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "**/deleteanuncio/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, Anuncio anuncio) {
        anuncioRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("anuncios", anuncioRepository.findAnuncioByUserId(usuario.getId(), PageRequest.of(0, 5, Sort.by("id"))));
        redirectAttributes.addFlashAttribute("anuncioobj", anuncio);
        redirectAttributes.addFlashAttribute("categorias", categoriaRepository.findAll());
        buscarUsuarioLogado();
        redirectAttributes.addFlashAttribute("usuario", usuario);
        return "redirect:/anuncio";
    }

    @RequestMapping(method = RequestMethod.GET, value = "**/detalhepag/{id}")
    public ModelAndView detalhepag(@PathVariable("id") Long id) {
        Anuncio anuncio = anuncioRepository.findById(id).get();
        ModelAndView model = new ModelAndView("detalhes");
        model.addObject("anuncios", anuncio);
        buscarUsuarioLogado();
        model.addObject("usuario", usuario);
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
