package com.alison.sistemaanuncios.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "anuncio")
@SequenceGenerator(name = "seq_anuncio", sequenceName = "seq_anuncio", allocationSize = 1, initialValue = 1)
public class Anuncio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_anuncio")
    private Long id;

    @NotEmpty(message = "O titulo não pode ser vazio!")
    @NotNull(message = "O titulo não pode ser nulo!")
    private String titulo;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] image;

    @NotEmpty(message = "Descrição não pode ser vazio!")
    @NotNull(message = "Descrição não pode ser nulo!")
    @Column(columnDefinition = "text")
    private String descricao;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date timestamp;

    @ManyToOne(fetch = FetchType.EAGER)
    private Categoria categoria;

    @NotNull(message = "Preço não pode ser nulo!")
    private BigDecimal preco;

    @ManyToOne
    private Usuario usuario;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }


    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
