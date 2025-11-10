package teste.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import teste.model.Paciente;
import teste.model.SinaisVitais;
import teste.repository.PacienteRepository;
import teste.repository.SinaisVitaisRepository;

@Service
public class SinaisVitaisAutomaticoService {

    @Autowired
    private PacienteRepository pacienteRepository;
    
    @Autowired
    private SinaisVitaisRepository sinaisVitaisRepository;
    
    private Random random = new Random();

    /**
     * Executa automaticamente a cada 5 minutos (300000 ms)
     * Gera novos sinais vitais para todos os pacientes ativos
     */
    @Scheduled(fixedRate = 300000) // 5 minutos
    public void gerarSinaisVitaisAutomaticos() {
        try {
            List<Paciente> pacientesAtivos = pacienteRepository.findByAtivoTrue();
            
            for (Paciente paciente : pacientesAtivos) {
                SinaisVitais novosVitais = gerarSinaisVitaisRealisticos(paciente);
                paciente.setSinaisVitais(novosVitais);
                pacienteRepository.save(paciente);
                
                System.out.println("Sinais vitais atualizados para paciente: " + paciente.getNome());
            }
            
            System.out.println("[" + LocalDateTime.now() + "] Gerados sinais vitais para " + 
                             pacientesAtivos.size() + " pacientes");
            
        } catch (Exception e) {
            System.err.println("Erro ao gerar sinais vitais automáticos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Gera sinais vitais realísticos baseados no perfil do paciente
     */
    private SinaisVitais gerarSinaisVitaisRealisticos(Paciente paciente) {
        SinaisVitais vitais = new SinaisVitais();
        
        // Valores base normais
        double oxigenioBase = 98.0;
        double temperaturaBase = 36.5;
        int batimentosBase = 72;
        int pressaoSistolicaBase = 120;
        int pressaoDiastolicaBase = 80;
        int frequenciaRespiratoriaBase = 16;
        
        // Variações baseadas na idade (simulada)
        int idadeSimulada = calcularIdadeSimulada(paciente);
        
        if (idadeSimulada > 65) {
            // Idosos: tendência a valores ligeiramente alterados
            oxigenioBase -= 1.0;
            batimentosBase += 5;
            pressaoSistolicaBase += 10;
            pressaoDiastolicaBase += 5;
        } else if (idadeSimulada < 30) {
            // Jovens: valores ligeiramente melhores
            oxigenioBase += 0.5;
            batimentosBase -= 3;
        }
        
        // Gerar valores com pequenas variações aleatórias
        double oxigenio = oxigenioBase + (random.nextGaussian() * 1.5); // ±1.5%
        double temperatura = temperaturaBase + (random.nextGaussian() * 0.3); // ±0.3°C
        int batimentos = batimentosBase + (int)(random.nextGaussian() * 8); // ±8 bpm
        int pressaoSistolica = pressaoSistolicaBase + (int)(random.nextGaussian() * 10); // ±10 mmHg
        int pressaoDiastolica = pressaoDiastolicaBase + (int)(random.nextGaussian() * 5); // ±5 mmHg
        int frequenciaRespiratoria = frequenciaRespiratoriaBase + (int)(random.nextGaussian() * 2); // ±2 rpm
        
        // Garantir limites mínimos e máximos realísticos
        oxigenio = Math.max(85.0, Math.min(100.0, oxigenio));
        temperatura = Math.max(35.0, Math.min(40.0, temperatura));
        batimentos = Math.max(45, Math.min(120, batimentos));
        pressaoSistolica = Math.max(90, Math.min(180, pressaoSistolica));
        pressaoDiastolica = Math.max(60, Math.min(110, pressaoDiastolica));
        frequenciaRespiratoria = Math.max(12, Math.min(25, frequenciaRespiratoria));
        
        // Definir valores
        vitais.setOxigenio(Math.round(oxigenio * 10.0) / 10.0); // 1 casa decimal
        vitais.setTemperatura(Math.round(temperatura * 10.0) / 10.0); // 1 casa decimal
        vitais.setBatimentos((double)batimentos);
        vitais.setPressaoSistolica((double)pressaoSistolica);
        vitais.setPressaoDiastolica((double)pressaoDiastolica);
        vitais.setFrequenciaRespiratoria((double)frequenciaRespiratoria);
        
        // Calcular status baseado nos valores
        vitais.setStatusOxigenio(calcularStatusOxigenio(vitais.getOxigenio()));
        vitais.setStatusTemperatura(calcularStatusTemperatura(vitais.getTemperatura()));
        vitais.setStatusBatimentos(calcularStatusBatimentos(vitais.getBatimentos()));
        vitais.setStatusPressao(calcularStatusPressao(vitais.getPressaoSistolica(), vitais.getPressaoDiastolica()));
        
        return vitais;
    }

    private int calcularIdadeSimulada(Paciente paciente) {
        // Simula idade baseada no nome ou outros fatores
        // Para esta demo, usa hash do nome
        return 25 + Math.abs(paciente.getNome().hashCode() % 50);
    }

    private String calcularStatusOxigenio(Double oxigenio) {
        if (oxigenio >= 96.0) return "NORMAL";
        else if (oxigenio >= 90.0) return "ATENCAO";
        else return "CRITICO";
    }

    private String calcularStatusTemperatura(Double temperatura) {
        if (temperatura >= 37.5) return "ELEVADA";
        else if (temperatura <= 35.5) return "BAIXA";
        else return "NORMAL";
    }

    private String calcularStatusBatimentos(Double batimentos) {
        if (batimentos >= 100) return "ELEVADO";
        else if (batimentos <= 60) return "BAIXO";
        else return "NORMAL";
    }

    private String calcularStatusPressao(Double sistolica, Double diastolica) {
        if (sistolica >= 140 || diastolica >= 90) return "ALTA";
        else if (sistolica <= 90 || diastolica <= 60) return "BAIXA";
        else return "NORMAL";
    }

    /**
     * Método para inicialização manual (se necessário)
     */
    public void gerarSinaisVitaisManual() {
        System.out.println("Gerando sinais vitais manualmente...");
        gerarSinaisVitaisAutomaticos();
    }
}