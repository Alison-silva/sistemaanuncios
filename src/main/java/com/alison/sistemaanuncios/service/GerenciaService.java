package com.alison.sistemaanuncios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class GerenciaService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void gerenciamentoAnuncio(Long idAnun) {
        String sql = "begin; UPDATE anuncio SET ativo = false WHERE  id = " + idAnun + "; commit;";
        jdbcTemplate.execute(sql);
    }
}
