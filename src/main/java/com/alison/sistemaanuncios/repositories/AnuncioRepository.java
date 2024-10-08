package com.alison.sistemaanuncios.repositories;

import com.alison.sistemaanuncios.model.Anuncio;
import com.alison.sistemaanuncios.model.Categoria;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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

    Page<Anuncio> findByAtivoTrue(Pageable pageable);

    @Query(nativeQuery = true, value = "select * from anuncio where usuario_id = ?1 ")
    Page<Anuncio> findAnuncioByUserId(Long usuarioid, Pageable pageable);

    default Page<Anuncio> findAnuncioByTituloPage(String titulo,  Pageable pageable) {
        Anuncio anuncio = new Anuncio();
        anuncio.setTitulo(titulo);
        anuncio.setAtivo(true);
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher("titulo", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("ativo", ExampleMatcher.GenericPropertyMatchers.exact());
        Example<Anuncio> example = Example.of(anuncio, exampleMatcher);
        Page<Anuncio> anuncios = findAll(example, pageable);
        return anuncios;
    }

    default Page<Anuncio> findAnuncioByCategoriaPage(Categoria categoria, Pageable pageable) {
        Anuncio anuncio = new Anuncio();
        anuncio.setCategoria(categoria);
        anuncio.setAtivo(true);
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher("categoria", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("ativo", ExampleMatcher.GenericPropertyMatchers.exact());
        Example<Anuncio> example = Example.of(anuncio, exampleMatcher);
        Page<Anuncio> anuncios = findAll(example, pageable);
        return anuncios;

    }

}
