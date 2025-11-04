package teste.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import teste.model.ConfiguracaoAlerta;

@Repository
public interface ConfiguracaoAlertaRepository extends JpaRepository<ConfiguracaoAlerta, Long> {
    
    // Buscar configurações por paciente
    List<ConfiguracaoAlerta> findByPacienteId(Long pacienteId);
    
    // Buscar configurações ativas
    List<ConfiguracaoAlerta> findByAtivoTrue();
    
    // Buscar configurações por paciente e tipo de sinal
    @Query("SELECT ca FROM ConfiguracaoAlerta ca WHERE ca.paciente.id = :pacienteId AND ca.tipoSinal = :tipoSinal AND ca.ativo = true")
    List<ConfiguracaoAlerta> findByPacienteIdAndTipoSinalAndAtivoTrue(@Param("pacienteId") Long pacienteId, @Param("tipoSinal") String tipoSinal);
    
    // Buscar configurações por tipo de sinal
    List<ConfiguracaoAlerta> findByTipoSinal(String tipoSinal);
    
    // Verificar se já existe configuração para paciente e tipo de sinal
    boolean existsByPacienteIdAndTipoSinalAndAtivoTrue(Long pacienteId, String tipoSinal);
}