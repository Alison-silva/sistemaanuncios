package com.alison.sistemaanuncios.controllers;

import com.alison.sistemaanuncios.model.Anuncio;
import com.alison.sistemaanuncios.model.Usuario;
import com.alison.sistemaanuncios.model.dto.AnuncioDTO;
import com.alison.sistemaanuncios.repositories.AnuncioRepository;
import com.alison.sistemaanuncios.repositories.CategoriaRepository;
import com.alison.sistemaanuncios.repositories.UsuarioRepository;
import com.alison.sistemaanuncios.service.GerenciaService;
import com.alison.sistemaanuncios.service.RelatorioAnuncio;
import com.alison.sistemaanuncios.service.ReportUtil;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GerenciaController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private AnuncioRepository anuncioRepository;

    @Autowired
    private GerenciaService gerenciaService;

    @Autowired
    private ReportUtil reportUtil;

    @Autowired
    private RelatorioAnuncio relatorioAnuncio;

    Usuario usuario = new Usuario();


    @RequestMapping(method = RequestMethod.GET, value = "**/gerenciamento")
    public ModelAndView gerenciamento() {
        ModelAndView model = new ModelAndView("gerenciamento");
        buscarUsuarioLogado();
        model.addObject("anuncios", anuncioRepository.findAll(PageRequest.of(0, 8, Sort.by("id"))));
        model.addObject("usuario", usuario);
        return model;
    }

    @GetMapping("**/gerenciapag")
    public ModelAndView carregaAnunPorPaginacao(@PageableDefault(size=5, sort = {"id"}) Pageable pageable,
                                               ModelAndView model) {
        Page<Anuncio> pageAnun = anuncioRepository.findAll(pageable);
        model.addObject("anuncios", pageAnun);
        model.setViewName("gerenciamento");
        buscarUsuarioLogado();
        model.addObject("usuario", usuario);
        return model;
    }

    @GetMapping("/imageanuncio/{idanun}")
    public void imageanuncio(@PathVariable("idanun") Long idanun, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");
        Anuncio anuncio = anuncioRepository.findById(idanun).get();
        InputStream is = new ByteArrayInputStream(anuncio.getImage());
        IOUtils.copy(is, response.getOutputStream());
    }

    @GetMapping("**/gerenciaanun/{idanun}")
    public String gerenciaanun(@PathVariable("idanun") Long idanun, RedirectAttributes redirectAttributes) {

        buscarUsuarioLogado();
        Anuncio anuncio = anuncioRepository.findById(idanun).get();

        gerenciaService.gerenciamentoAnuncio(anuncio.getId());
        redirectAttributes.addFlashAttribute("anuncios", anuncioRepository.findAll(PageRequest.of(0, 8, Sort.by("id"))));
        redirectAttributes.addFlashAttribute("usuario", usuario);
        return "redirect:/gerenciamento";
    }

    @GetMapping("**/relatoriopdf")
    public void imprimePdf(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        List<AnuncioDTO> anuncioDTO = new ArrayList<AnuncioDTO>();
        anuncioDTO = relatorioAnuncio.GerarRelaAnuncios();

        byte[] pdf = reportUtil.gerarRelatorio(anuncioDTO,"relatorio-anuncio", request.getServletContext());
        response.setContentLength(pdf.length);
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", "relatorio.pdf");
        response.setHeader(headerKey, headerValue);
        response.getOutputStream().write(pdf);
    }

    private void buscarUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String login = authentication.getName();
            usuario = usuarioRepository.buscarUsuarioLogin(login).get(0);
        }
    }
}
