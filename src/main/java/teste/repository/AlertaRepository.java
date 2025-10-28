package teste.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import teste.model.Alerta;
import teste.model.Alerta.NivelPrioridade;
import teste.model.Alerta.TipoAlerta;
import teste.model.Paciente;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {
    
    // Buscar alertas ativos
    List<Alerta> findByAtivoTrue();
    
    // Buscar alertas não lidos
    List<Alerta> findByLidoFalseAndAtivoTrueOrderByDataCriacaoDesc();
    
    // Buscar alertas por paciente
    List<Alerta> findByPacienteAndAtivoTrueOrderByDataCriacaoDesc(Paciente paciente);
    
    // Buscar alertas por prioridade
    List<Alerta> findByPrioridadeAndAtivoTrueOrderByDataCriacaoDesc(NivelPrioridade prioridade);
    
    // Buscar alertas por tipo
    List<Alerta> findByTipoAndAtivoTrueOrderByDataCriacaoDesc(TipoAlerta tipo);
    
    // Buscar alertas críticos não lidos
    @Query("SELECT a FROM Alerta a WHERE a.prioridade = 'CRITICA' AND a.lido = false AND a.ativo = true ORDER BY a.dataCriacao DESC")
    List<Alerta> findAlertasCriticosNaoLidos();
    
    // Buscar alertas por período
    @Query("SELECT a FROM Alerta a WHERE a.dataCriacao BETWEEN :dataInicio AND :dataFim AND a.ativo = true ORDER BY a.dataCriacao DESC")
    List<Alerta> findByPeriodo(@Param("dataInicio") LocalDateTime dataInicio, @Param("dataFim") LocalDateTime dataFim);
    
    // Contar alertas não lidos por paciente
    Long countByPacienteAndLidoFalseAndAtivoTrue(Paciente paciente);
}