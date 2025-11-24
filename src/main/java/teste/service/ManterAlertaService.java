package teste.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import teste.model.Alerta;
import teste.model.Paciente;
import teste.model.SinaisVitais;
import teste.model.enums.NivelPrioridade;
import teste.model.enums.TipoAlerta;
import teste.repository.AlertaRepository;

@Service
@Transactional
public class ManterAlertaService {
    
    @Autowired
    private AlertaRepository alertaRepository;
    
    @Autowired
    private ManterPacienteService pacienteService;
    
    public Alerta salvar(Alerta alerta) {
        return alertaRepository.save(alerta);
    }
    
    public Alerta criarAlerta(Paciente paciente, TipoAlerta tipo, String mensagem, NivelPrioridade prioridade) {
        Alerta alerta = new Alerta();
        alerta.setPaciente(paciente);
        alerta.setTipo(tipo);
        alerta.setMensagem(mensagem);
        alerta.setPrioridade(prioridade);
        alerta.setLido(false);
        alerta.setAtivo(true);
        
        return salvar(alerta);
    }
    
    public void marcarComoLido(Long alertaId) {
        Alerta alerta = buscarPorId(alertaId);
        if (alerta != null) {
            alerta.setLido(true);
            alerta.setDataLeitura(LocalDateTime.now());
            alertaRepository.save(alerta);
        }
    }
    
    public void desativarAlerta(Long alertaId) {
        Alerta alerta = buscarPorId(alertaId);
        if (alerta != null) {
            alerta.setAtivo(false);
            alertaRepository.save(alerta);
        }
    }
    
    // Método principal para verificar sinais vitais e gerar alertas
    public void verificarSinaisVitaisECriarAlertas(Paciente paciente) {
        if (paciente.getSinaisVitais() == null) {
            return;
        }
        
        SinaisVitais sinais = paciente.getSinaisVitais();
        
        // Verificar oxigenação
        if (sinais.getOxigenio() != null) {
            if (sinais.getOxigenio() < 95.0) {
                criarAlerta(paciente, 
                    TipoAlerta.OXIGENIO_BAIXO, 
                    String.format("Oxigenação baixa detectada: %.1f%%. Paciente: %s", 
                        sinais.getOxigenio(), paciente.getNome()),
                    sinais.getOxigenio() < 90.0 ? NivelPrioridade.CRITICA : NivelPrioridade.ALTA);
            }
        }
        
        // Verificar temperatura
        if (sinais.getTemperatura() != null) {
            if (sinais.getTemperatura() > 38.0) {
                criarAlerta(paciente, 
                    TipoAlerta.TEMPERATURA_ALTA, 
                    String.format("Febre detectada: %.1f°C. Paciente: %s", 
                        sinais.getTemperatura(), paciente.getNome()),
                    sinais.getTemperatura() > 39.0 ? NivelPrioridade.CRITICA : NivelPrioridade.ALTA);
            } else if (sinais.getTemperatura() < 35.0) {
                criarAlerta(paciente, 
                    TipoAlerta.TEMPERATURA_BAIXA, 
                    String.format("Hipotermia detectada: %.1f°C. Paciente: %s", 
                        sinais.getTemperatura(), paciente.getNome()),
                    NivelPrioridade.CRITICA);
            }
        }
        
        // Verificar batimentos
        if (sinais.getBatimentos() != null) {
            if (sinais.getBatimentos() > 120) {
                criarAlerta(paciente, 
                    TipoAlerta.BATIMENTOS_ALTO, 
                    String.format("Taquicardia detectada: %d bpm. Paciente: %s", 
                        sinais.getBatimentos(), paciente.getNome()),
                    sinais.getBatimentos() > 150 ? NivelPrioridade.CRITICA : NivelPrioridade.ALTA);
            } else if (sinais.getBatimentos() < 50) {
                criarAlerta(paciente, 
                    TipoAlerta.BATIMENTOS_BAIXO, 
                    String.format("Bradicardia detectada: %d bpm. Paciente: %s", 
                        sinais.getBatimentos(), paciente.getNome()),
                    NivelPrioridade.ALTA);
            }
        }
    }
    
    @Transactional(readOnly = true)
    public List<Alerta> buscarTodos() {
        return alertaRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<Alerta> buscarAtivos() {
        return alertaRepository.findByAtivoTrue();
    }
    
    @Transactional(readOnly = true)
    public List<Alerta> buscarNaoLidos() {
        return alertaRepository.findByLidoFalseAndAtivoTrueOrderByDataCriacaoDesc();
    }
    
    @Transactional(readOnly = true)
    public List<Alerta> buscarCriticosNaoLidos() {
        return alertaRepository.findAlertasCriticosNaoLidos();
    }
    
    @Transactional(readOnly = true)
    public List<Alerta> buscarPorPaciente(Long pacienteId) {
        Paciente paciente = pacienteService.buscarPeloCodigo(pacienteId);
        if (paciente != null) {
            return alertaRepository.findByPacienteAndAtivoTrueOrderByDataCriacaoDesc(paciente);
        }
        return List.of();
    }
    
    @Transactional(readOnly = true)
    public List<Alerta> buscarPorPrioridade(NivelPrioridade prioridade) {
        return alertaRepository.findByPrioridadeAndAtivoTrueOrderByDataCriacaoDesc(prioridade);
    }
    
    @Transactional(readOnly = true)
    public Alerta buscarPorId(Long id) {
        Optional<Alerta> alerta = alertaRepository.findById(id);
        return alerta.orElse(null);
    }
    
    @Transactional(readOnly = true)
    public Long contarAlertasNaoLidosPorPaciente(Long pacienteId) {
        Paciente paciente = pacienteService.buscarPeloCodigo(pacienteId);
        if (paciente != null) {
            return alertaRepository.countByPacienteAndLidoFalseAndAtivoTrue(paciente);
        }
        return 0L;
    }
}