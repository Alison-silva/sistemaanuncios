package com.alison.sistemaanuncios.repositories;

import com.alison.sistemaanuncios.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {



}
