package teste.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import teste.model.Paciente;
import teste.repository.PacienteRepository;

@Service
@Transactional
public class ManterPacienteService {
    
    @Autowired    
    private PacienteRepository pacienteRepository;
    
    public Paciente salvar(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }
    
    public void excluir(Long id) {
        // Verificar se o paciente existe antes de excluir
        Optional<Paciente> pacienteOpt = pacienteRepository.findById(id);
        if (pacienteOpt.isPresent()) {
            pacienteRepository.deleteById(id);
        } else {
            throw new RuntimeException("Paciente não encontrado com ID: " + id);
        }
    }
    
    public void excluir(Paciente paciente) {
        if (paciente != null && paciente.getId() != null) {
            excluir(paciente.getId());
        }
    }

    @Transactional(readOnly = true)
    public List<Paciente> buscarTodos() {
        return pacienteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Paciente buscarPeloCodigo(Long id) {
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        return paciente.orElse(null);
    }

    public Object gerarHistoricoSimulado(Paciente paciente, String periodo) {
        if (paciente.getSinaisVitais() == null) {
            return new HistoricoVazio();
        }

        var sinais = paciente.getSinaisVitais();
        int dataPoints;
        String[] labels;

        switch (periodo) {
            case "1day":
                dataPoints = 24;
                labels = new String[dataPoints];
                for (int i = 0; i < dataPoints; i++) {
                    labels[i] = String.format("%02d:00", i);
                }
                break;
            case "1month":
                dataPoints = 30;
                labels = new String[dataPoints];
                for (int i = 1; i <= dataPoints; i++) {
                    labels[i-1] = String.valueOf(i);
                }
                break;
            case "1year":
                dataPoints = 12;
                labels = new String[]{"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", 
                                    "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
                break;
            default:
                return new HistoricoVazio();
        }

        // Gerar dados simulados com base nos valores atuais
        double[] batimentos = gerarDadosSimulados(sinais.getBatimentos(), dataPoints, 0.1);
        double[] oxigenio = gerarDadosSimulados(sinais.getOxigenio(), dataPoints, 0.05);
        double[] temperatura = gerarDadosSimulados(sinais.getTemperatura(), dataPoints, 0.02);

        return new HistoricoSinaisVitais(labels, batimentos, oxigenio, temperatura);
    }

    private double[] gerarDadosSimulados(double valorBase, int pontos, double variacao) {
        double[] dados = new double[pontos];
        double valorAtual = valorBase;

        for (int i = 0; i < pontos; i++) {
            // Adicionar variação aleatória
            double mudanca = (Math.random() - 0.5) * 2 * variacao * valorBase;
            valorAtual += mudanca;

            // Manter dentro de limites realísticos
            if (valorBase > 90) { // Oxigenação
                valorAtual = Math.max(90, Math.min(100, valorAtual));
            } else if (valorBase > 30) { // Batimentos
                valorAtual = Math.max(50, Math.min(150, valorAtual));
            } else { // Temperatura
                valorAtual = Math.max(35.0, Math.min(40.0, valorAtual));
            }

            dados[i] = Math.round(valorAtual * 100.0) / 100.0;
        }

        return dados;
    }

    // Classes internas para retorno JSON
    public static class HistoricoSinaisVitais {
        private String[] labels;
        private double[] batimentos;
        private double[] oxigenio;
        private double[] temperatura;

        public HistoricoSinaisVitais(String[] labels, double[] batimentos, double[] oxigenio, double[] temperatura) {
            this.labels = labels;
            this.batimentos = batimentos;
            this.oxigenio = oxigenio;
            this.temperatura = temperatura;
        }

        public String[] getLabels() { return labels; }
        public double[] getBatimentos() { return batimentos; }
        public double[] getOxigenio() { return oxigenio; }
        public double[] getTemperatura() { return temperatura; }
    }

    public static class HistoricoVazio {
        private String[] labels = {};
        private double[] batimentos = {};
        private double[] oxigenio = {};
        private double[] temperatura = {};

        public String[] getLabels() { return labels; }
        public double[] getBatimentos() { return batimentos; }
        public double[] getOxigenio() { return oxigenio; }
        public double[] getTemperatura() { return temperatura; }
    }
}