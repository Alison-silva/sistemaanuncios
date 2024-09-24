package com.alison.sistemaanuncios.repositories;

import com.alison.sistemaanuncios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("select u from Usuario u where u.login = ?1")
    Usuario findUserByLogin(String login);

    @Query(value = "select constraint_name from information_schema.constraint_column_usage where table_name = 'usuarios_role' and column_name = 'role_id' and constraint_name <> 'unique_role_user';", nativeQuery = true)
    String consultaConstraintRole();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "insert into usuarios_role (usuario_id, role_id) values (?1, (select id from role where desc_role = 'ANUNCIANTE'));")
    void insereAcessoRolePadrao(Long idUser);

}
