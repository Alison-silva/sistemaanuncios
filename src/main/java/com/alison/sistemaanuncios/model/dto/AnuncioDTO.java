package com.alison.sistemaanuncios.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class AnuncioDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private BigDecimal preco;
    private String titulo;
    private Long categoria_id;
    private String nome_da_categoria;
    private String nome_anunciante;

    public Long getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(Long categoria_id) {
        this.categoria_id = categoria_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNome_da_categoria() {
        return nome_da_categoria;
    }

    public void setNome_da_categoria(String nome_da_categoria) {
        this.nome_da_categoria = nome_da_categoria;
    }

    public String getNome_anunciante() {
        return nome_anunciante;
    }

    public void setNome_anunciante(String nome_anunciante) {
        this.nome_anunciante = nome_anunciante;
    }
}
