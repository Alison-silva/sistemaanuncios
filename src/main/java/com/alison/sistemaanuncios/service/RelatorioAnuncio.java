package com.alison.sistemaanuncios.service;

import com.alison.sistemaanuncios.model.Anuncio;
import com.alison.sistemaanuncios.model.dto.AnuncioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RelatorioAnuncio {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<AnuncioDTO> GerarRelaAnuncios(){

        List<AnuncioDTO> retorno = new ArrayList<AnuncioDTO>();

        String sql = " SELECT a.id, a.preco, a.titulo, a.categoria_id, "
                + " c.nomecategoria AS nome_da_categoria, "
                + " u.nome AS nome_anunciante "
                + " FROM anuncio a "
                + " INNER JOIN usuario u ON a.usuario_id = u.id "
                + " INNER JOIN categoria c ON a.categoria_id = c.id "
                + " ORDER BY a.id ASC; ";

        retorno = jdbcTemplate.query(sql, new BeanPropertyRowMapper(AnuncioDTO.class));

        return retorno;

    }
}
