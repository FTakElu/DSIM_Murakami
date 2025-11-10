package teste.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teste.model.Alerta;
import teste.model.Paciente;
import teste.model.SinaisVitais;
import teste.repository.PacienteRepository;
import teste.repository.SinaisVitaisRepository;

@Service
public class SinaisVitaisService {

    @Autowired
    private SinaisVitaisRepository sinaisVitaisRepository;
    
    @Autowired
    private PacienteRepository pacienteRepository;
    
    @Autowired
    private AlertaService alertaService;
    
    private final Random random = new Random();
    
    /**
     * Gera sinais vitais automáticos para todos os pacientes
     */
    public void gerarSinaisVitaisAutomaticos() {
        List<Paciente> pacientes = pacienteRepository.findAll();
        
        for (Paciente paciente : pacientes) {
            if (paciente.getSinaisVitais() != null) {
                SinaisVitais sinaisVitais = paciente.getSinaisVitais();
                
                // Gerar novos valores aleatórios dentro de faixas realistas
                sinaisVitais.setOxigenio(gerarOxigenio());
                sinaisVitais.setStatusOxigenio(determinarStatusOxigenio(sinaisVitais.getOxigenio()));
                
                sinaisVitais.setTemperatura(gerarTemperatura());
                sinaisVitais.setStatusTemperatura(determinarStatusTemperatura(sinaisVitais.getTemperatura()));
                
                sinaisVitais.setBatimentos(gerarBatimentos());
                sinaisVitais.setStatusBatimentos(determinarStatusBatimentos(sinaisVitais.getBatimentos()));
                
                // Salvar os novos sinais vitais
                sinaisVitaisRepository.save(sinaisVitais);
                
                // Verificar se deve gerar alertas
                verificarEGerarAlertas(paciente, sinaisVitais);
            }
        }
    }
    
    /**
     * Gera saturação de oxigênio entre 85% e 100%
     */
    private Double gerarOxigenio() {
        // 70% chance de valores normais (95-100%), 30% chance de valores alterados (85-94%)
        if (random.nextDouble() < 0.7) {
            return 95.0 + (random.nextDouble() * 5.0); // 95-100%
        } else {
            return 85.0 + (random.nextDouble() * 10.0); // 85-95%
        }
    }
    
    /**
     * Gera temperatura corporal entre 35°C e 40°C
     */
    private Double gerarTemperatura() {
        // 70% chance de valores normais (36.1-37.2°C), 30% chance de valores alterados
        if (random.nextDouble() < 0.7) {
            return 36.1 + (random.nextDouble() * 1.1); // 36.1-37.2°C
        } else {
            // Valores alterados: febre ou hipotermia
            if (random.nextBoolean()) {
                return 37.5 + (random.nextDouble() * 2.5); // 37.5-40°C (febre)
            } else {
                return 35.0 + (random.nextDouble() * 1.0); // 35-36°C (hipotermia)
            }
        }
    }
    
    /**
     * Gera batimentos cardíacos entre 40 e 150 bpm
     */
    private Integer gerarBatimentos() {
        // 70% chance de valores normais (60-100 bpm), 30% chance de valores alterados
        if (random.nextDouble() < 0.7) {
            return 60 + random.nextInt(41); // 60-100 bpm
        } else {
            // Valores alterados: bradicardia ou taquicardia
            if (random.nextBoolean()) {
                return 100 + random.nextInt(51); // 100-150 bpm (taquicardia)
            } else {
                return 40 + random.nextInt(21); // 40-60 bpm (bradicardia)
            }
        }
    }
    
    /**
     * Determina status da saturação de oxigênio
     */
    private String determinarStatusOxigenio(Double oxigenio) {
        if (oxigenio >= 95.0) {
            return "Normal";
        } else if (oxigenio >= 90.0) {
            return "Baixo";
        } else {
            return "Crítico";
        }
    }
    
    /**
     * Determina status da temperatura corporal
     */
    private String determinarStatusTemperatura(Double temperatura) {
        if (temperatura < 36.0) {
            return "Hipotermia";
        } else if (temperatura <= 37.2) {
            return "Normal";
        } else if (temperatura <= 38.0) {
            return "Febre Baixa";
        } else {
            return "Febre Alta";
        }
    }
    
    /**
     * Determina status dos batimentos cardíacos
     */
    private String determinarStatusBatimentos(Integer batimentos) {
        if (batimentos < 60) {
            return "Bradicardia";
        } else if (batimentos <= 100) {
            return "Normal";
        } else {
            return "Taquicardia";
        }
    }
    
    /**
     * Verifica se deve gerar alertas baseado nos sinais vitais
     */
    private void verificarEGerarAlertas(Paciente paciente, SinaisVitais sinaisVitais) {
        try {
            // Verificar oxigênio baixo
            if (sinaisVitais.getOxigenio() < 90.0) {
                alertaService.criarAlerta(
                    paciente,
                    Alerta.TipoAlerta.SINAIS_VITAIS,
                    "Saturação de oxigênio crítica: " + String.format("%.1f%%", sinaisVitais.getOxigenio()),
                    Alerta.NivelPrioridade.CRITICO
                );
            }
            
            // Verificar temperatura alta
            if (sinaisVitais.getTemperatura() > 38.5) {
                alertaService.criarAlerta(
                    paciente,
                    Alerta.TipoAlerta.SINAIS_VITAIS,
                    "Febre alta detectada: " + String.format("%.1f°C", sinaisVitais.getTemperatura()),
                    Alerta.NivelPrioridade.ALTO
                );
            }
            
            // Verificar batimentos anômalos
            if (sinaisVitais.getBatimentos() < 50 || sinaisVitais.getBatimentos() > 120) {
                String tipo = sinaisVitais.getBatimentos() < 50 ? "Bradicardia severa" : "Taquicardia severa";
                alertaService.criarAlerta(
                    paciente,
                    Alerta.TipoAlerta.SINAIS_VITAIS,
                    tipo + ": " + sinaisVitais.getBatimentos() + " bpm",
                    Alerta.NivelPrioridade.ALTO
                );
            }
        } catch (Exception e) {
            // Log do erro mas continua o processamento para outros pacientes
            System.err.println("Erro ao criar alerta para paciente " + paciente.getId() + ": " + e.getMessage());
        }
    }
}