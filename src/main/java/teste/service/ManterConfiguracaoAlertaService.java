package teste.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import teste.model.ConfiguracaoAlerta;
import teste.model.Paciente;
import teste.repository.ConfiguracaoAlertaRepository;
import teste.repository.PacienteRepository;

@Service
@Transactional
public class ManterConfiguracaoAlertaService {
    
    @Autowired
    private ConfiguracaoAlertaRepository configuracaoAlertaRepository;
    
    @Autowired
    private PacienteRepository pacienteRepository;
    
    public ConfiguracaoAlerta salvar(ConfiguracaoAlerta configuracao) {
        return configuracaoAlertaRepository.save(configuracao);
    }
    
    public ConfiguracaoAlerta criarConfiguracao(Long pacienteId, String tipoSinal, 
                                              Double valorMinimo, Double valorMaximo, 
                                              String prioridade, Boolean ativo) {
        
        // Verificar se paciente existe
        Optional<Paciente> paciente = pacienteRepository.findById(pacienteId);
        if (paciente.isEmpty()) {
            throw new IllegalArgumentException("Paciente não encontrado");
        }
        
        // Verificar se já existe configuração ativa para este paciente e tipo de sinal
        if (configuracaoAlertaRepository.existsByPacienteIdAndTipoSinalAndAtivoTrue(pacienteId, tipoSinal)) {
            throw new IllegalArgumentException("Já existe uma configuração ativa para este paciente e tipo de sinal");
        }
        
        // Validar valores
        if (valorMinimo != null && valorMaximo != null && valorMinimo >= valorMaximo) {
            throw new IllegalArgumentException("Valor mínimo deve ser menor que o valor máximo");
        }
        
        ConfiguracaoAlerta configuracao = new ConfiguracaoAlerta();
        configuracao.setPaciente(paciente.get());
        configuracao.setTipoSinal(tipoSinal);
        configuracao.setValorMinimo(valorMinimo);
        configuracao.setValorMaximo(valorMaximo);
        configuracao.setPrioridade(prioridade);
        configuracao.setAtivo(ativo != null ? ativo : true);
        
        return salvar(configuracao);
    }
    
    public void excluir(Long id) {
        configuracaoAlertaRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public List<ConfiguracaoAlerta> buscarTodas() {
        return configuracaoAlertaRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<ConfiguracaoAlerta> buscarAtivas() {
        return configuracaoAlertaRepository.findByAtivoTrue();
    }
    
    @Transactional(readOnly = true)
    public ConfiguracaoAlerta buscarPorId(Long id) {
        Optional<ConfiguracaoAlerta> configuracao = configuracaoAlertaRepository.findById(id);
        return configuracao.orElse(null);
    }
    
    @Transactional(readOnly = true)
    public List<ConfiguracaoAlerta> buscarPorPaciente(Long pacienteId) {
        return configuracaoAlertaRepository.findByPacienteId(pacienteId);
    }
    
    @Transactional(readOnly = true)
    public List<ConfiguracaoAlerta> buscarPorTipoSinal(String tipoSinal) {
        return configuracaoAlertaRepository.findByTipoSinal(tipoSinal);
    }
    
    @Transactional(readOnly = true)
    public List<ConfiguracaoAlerta> buscarPorPacienteETipo(Long pacienteId, String tipoSinal) {
        return configuracaoAlertaRepository.findByPacienteIdAndTipoSinalAndAtivoTrue(pacienteId, tipoSinal);
    }
    
    /**
     * Verificar se um valor está fora dos limites configurados
     */
    @Transactional(readOnly = true)
    public boolean valorForaDosLimites(Long pacienteId, String tipoSinal, Double valor) {
        List<ConfiguracaoAlerta> configuracoes = buscarPorPacienteETipo(pacienteId, tipoSinal);
        
        for (ConfiguracaoAlerta config : configuracoes) {
            if (config.getValorMinimo() != null && valor < config.getValorMinimo()) {
                return true;
            }
            if (config.getValorMaximo() != null && valor > config.getValorMaximo()) {
                return true;
            }
        }
        
        return false;
    }
}