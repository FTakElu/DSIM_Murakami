package teste.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import teste.model.Paciente;
import teste.service.ManterPacienteService;

@RestController
@RequestMapping("/api/pacientes")
@CrossOrigin(origins = "*") // Permite requisições do frontend
public class PacienteController {

    @Autowired
    private ManterPacienteService pacienteService;

    @GetMapping
    public ResponseEntity<List<Paciente>> listarTodos() {
        try {
            List<Paciente> pacientes = pacienteService.buscarTodos();
            return ResponseEntity.ok(pacientes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Long id) {
        try {
            Paciente paciente = pacienteService.buscarPeloCodigo(id);
            if (paciente != null) {
                return ResponseEntity.ok(paciente);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Paciente> criarPaciente(@RequestBody Paciente paciente) {
        try {
            // Remove o ID para garantir que seja um novo registro
            paciente.setId(null);
            
            Paciente pacienteSalvo = pacienteService.salvar(paciente);
            return ResponseEntity.status(HttpStatus.CREATED).body(pacienteSalvo);
        } catch (Exception e) {
            System.err.println("Erro ao salvar paciente: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> atualizarPaciente(@PathVariable Long id, @RequestBody Paciente paciente) {
        try {
            Paciente pacienteExistente = pacienteService.buscarPeloCodigo(id);
            if (pacienteExistente != null) {
                paciente.setId(id);
                Paciente pacienteAtualizado = pacienteService.salvar(paciente);
                return ResponseEntity.ok(pacienteAtualizado);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPaciente(@PathVariable Long id) {
        try {
            Paciente pacienteExistente = pacienteService.buscarPeloCodigo(id);
            if (pacienteExistente != null) {
                pacienteService.excluir(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Long> contarPacientes() {
        try {
            List<Paciente> pacientes = pacienteService.buscarTodos();
            return ResponseEntity.ok((long) pacientes.size());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}/historico-sinais/{periodo}")
    public ResponseEntity<?> obterHistoricoSinaisVitais(@PathVariable Long id, @PathVariable String periodo) {
        try {
            Paciente paciente = pacienteService.buscarPeloCodigo(id);
            if (paciente == null) {
                return ResponseEntity.notFound().build();
            }

            // Simulação de dados históricos baseados nos sinais vitais atuais
            var historicoSimulado = pacienteService.gerarHistoricoSimulado(paciente, periodo);
            return ResponseEntity.ok(historicoSimulado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}