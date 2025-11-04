package teste.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import teste.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Buscar usuário por email (para login)
    Optional<Usuario> findByEmail(String email);
    
    // Verificar se email já existe
    boolean existsByEmail(String email);
    
    // Buscar usuários ativos
    List<Usuario> findByAtivoTrue();
    
    // Buscar usuários por nome (busca parcial)
    @Query("SELECT u FROM Usuario u WHERE u.nome LIKE %:nome% AND u.ativo = true")
    List<Usuario> findByNomeContaining(@Param("nome") String nome);
}