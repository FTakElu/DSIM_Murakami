package teste.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import teste.model.Paciente;
import teste.model.Usuario;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    
    // Buscar pacientes por usuário responsável
    List<Paciente> findByUsuarioResponsavel(Usuario usuario);
    
    // Buscar pacientes por ID do usuário responsável
    List<Paciente> findByUsuarioResponsavelId(Long usuarioId);
    
    // Buscar pacientes ativos
    List<Paciente> findByAtivoTrue();
    
    // Verificar se o paciente pertence ao usuário
    boolean existsByIdAndUsuarioResponsavelId(Long pacienteId, Long usuarioId);
}