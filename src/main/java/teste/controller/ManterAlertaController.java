package teste.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import teste.model.ConfiguracaoAlerta;
import teste.service.ManterConfiguracaoAlertaService;

@RestController
@RequestMapping("/api/configuracao-alertas")
public class ManterAlertaController {
    
    @Autowired
    private ManterConfiguracaoAlertaService configuracaoAlertaService;
    
    /**
     * Listar todas as configurações de alerta
     */
    @GetMapping
    public ResponseEntity<List<ConfiguracaoAlerta>> listarConfiguracoes() {
        List<ConfiguracaoAlerta> configuracoes = configuracaoAlertaService.buscarTodas();
        return ResponseEntity.ok(configuracoes);
    }
    
    /**
     * Buscar configuração por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        ConfiguracaoAlerta configuracao = configuracaoAlertaService.buscarPorId(id);
        if (configuracao == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(configuracao);
    }
    
    /**
     * Buscar configuração por paciente
     */
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<ConfiguracaoAlerta>> buscarPorPaciente(@PathVariable Long pacienteId) {
        List<ConfiguracaoAlerta> configuracoes = configuracaoAlertaService.buscarPorPaciente(pacienteId);
        return ResponseEntity.ok(configuracoes);
    }
    
    /**
     * Criar nova configuração de alerta
     */
    @PostMapping
    public ResponseEntity<?> criarConfiguracao(@RequestBody Map<String, Object> dados) {
        try {
            Long pacienteId = Long.valueOf(dados.get("pacienteId").toString());
            String tipoSinal = dados.get("tipoSinal").toString();
            Double valorMinimo = dados.get("valorMinimo") != null ? 
                Double.valueOf(dados.get("valorMinimo").toString()) : null;
            Double valorMaximo = dados.get("valorMaximo") != null ? 
                Double.valueOf(dados.get("valorMaximo").toString()) : null;
            String prioridade = dados.get("prioridade").toString();
            Boolean ativo = dados.get("ativo") != null ? 
                Boolean.valueOf(dados.get("ativo").toString()) : true;
            
            ConfiguracaoAlerta configuracao = configuracaoAlertaService.criarConfiguracao(
                pacienteId, tipoSinal, valorMinimo, valorMaximo, prioridade, ativo);
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Configuração de alerta criada com sucesso",
                "configuracao", configuracao
            ));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
    
    /**
     * Atualizar configuração de alerta
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarConfiguracao(@PathVariable Long id, @RequestBody Map<String, Object> dados) {
        try {
            ConfiguracaoAlerta configuracaoExistente = configuracaoAlertaService.buscarPorId(id);
            if (configuracaoExistente == null) {
                return ResponseEntity.notFound().build();
            }
            
            if (dados.containsKey("tipoSinal")) {
                configuracaoExistente.setTipoSinal(dados.get("tipoSinal").toString());
            }
            
            if (dados.containsKey("valorMinimo")) {
                configuracaoExistente.setValorMinimo(dados.get("valorMinimo") != null ? 
                    Double.valueOf(dados.get("valorMinimo").toString()) : null);
            }
            
            if (dados.containsKey("valorMaximo")) {
                configuracaoExistente.setValorMaximo(dados.get("valorMaximo") != null ? 
                    Double.valueOf(dados.get("valorMaximo").toString()) : null);
            }
            
            if (dados.containsKey("prioridade")) {
                configuracaoExistente.setPrioridade(dados.get("prioridade").toString());
            }
            
            if (dados.containsKey("ativo")) {
                configuracaoExistente.setAtivo(Boolean.valueOf(dados.get("ativo").toString()));
            }
            
            ConfiguracaoAlerta configuracaoAtualizada = configuracaoAlertaService.salvar(configuracaoExistente);
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Configuração atualizada com sucesso",
                "configuracao", configuracaoAtualizada
            ));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
    
    /**
     * Excluir configuração de alerta
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirConfiguracao(@PathVariable Long id) {
        try {
            ConfiguracaoAlerta configuracao = configuracaoAlertaService.buscarPorId(id);
            if (configuracao == null) {
                return ResponseEntity.notFound().build();
            }
            
            configuracaoAlertaService.excluir(id);
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Configuração excluída com sucesso"
            ));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
    
    /**
     * Ativar/Desativar configuração
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<?> alterarStatus(@PathVariable Long id, @RequestParam boolean ativo) {
        try {
            ConfiguracaoAlerta configuracao = configuracaoAlertaService.buscarPorId(id);
            if (configuracao == null) {
                return ResponseEntity.notFound().build();
            }
            
            configuracao.setAtivo(ativo);
            configuracaoAlertaService.salvar(configuracao);
            
            String mensagem = ativo ? "Configuração ativada com sucesso" : "Configuração desativada com sucesso";
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", mensagem
            ));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
}