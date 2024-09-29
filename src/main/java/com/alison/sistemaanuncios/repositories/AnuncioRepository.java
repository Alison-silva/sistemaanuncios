package com.alison.sistemaanuncios.repositories;

import com.alison.sistemaanuncios.model.Anuncio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {
}
