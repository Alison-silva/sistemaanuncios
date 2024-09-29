package com.alison.sistemaanuncios.repositories;

import com.alison.sistemaanuncios.model.Anuncio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {

    @Query("select a from Anuncio a where a.usuario.id = ?1")
    public List<Anuncio> getAnuncios(Long usuarioid);

    @Query(nativeQuery = true, value = "select * from anuncio where usuario_id = ?1 ")
    Page<Anuncio> findAnuncioByUserId(Long usuarioid, Pageable pageable);

}
