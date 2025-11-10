package teste.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import teste.service.SinaisVitaisService;

@Component
public class SinaisVitaisScheduler {

    @Autowired
    private SinaisVitaisService sinaisVitaisService;
    
    /**
     * Executa automaticamente a cada 5 minutos (300.000 ms)
     * para gerar novos sinais vitais para todos os pacientes
     */
    @Scheduled(fixedRate = 300000) // 5 minutos = 300.000 milissegundos
    public void gerarSinaisVitaisAutomaticos() {
        try {
            System.out.println("Iniciando geração automática de sinais vitais...");
            sinaisVitaisService.gerarSinaisVitaisAutomaticos();
            System.out.println("Geração automática de sinais vitais concluída com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro na geração automática de sinais vitais: " + e.getMessage());
            e.printStackTrace();
        }
    }
}