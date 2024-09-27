package com.alison.sistemaanuncios.repositories;

import com.alison.sistemaanuncios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("select u from Usuario u where u.login = ?1")
    Usuario findUserByLogin(String login);

    @Query("from Usuario where login = ?1")
    public List<Usuario> buscarUsuarioLogin(String login);

    @Query(value = "select constraint_name from information_schema.constraint_column_usage where table_name = 'usuarios_role' and column_name = 'role_id' and constraint_name <> 'unique_role_user';", nativeQuery = true)
    String consultaConstraintRole();

    @Query(nativeQuery = true, value = "SELECT r.desc_role FROM usuario u JOIN usuarios_role ur ON u.id = ur.usuario_id JOIN role r ON ur.role_id = r.id WHERE u.id = ?1")
    String consultaDescRole(Long id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "insert into usuarios_role (usuario_id, role_id) values (?1, (select id from role where desc_role = 'ROLE_ANUNCIANTE'));")
    void insereAcessoRolePadrao(Long idUser);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO usuarios_role (usuario_id, role_id) VALUES (?1, (SELECT id FROM role WHERE desc_role = 'ROLE_ADMIN'))")
    void insereAcessoRoleAdmin(Long idUser);


}
