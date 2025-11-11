package teste.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import teste.model.Paciente;
import teste.model.Usuario;
import teste.repository.PacienteRepository;
import teste.service.ManterPacienteService;
import teste.service.UsuarioService;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

	@Autowired
	private ManterPacienteService pacienteService;

	@Autowired
	private PacienteRepository pacienteRepository;

	@Autowired
	private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Paciente>> listarTodos() {
        try {
            System.out.println("🔍 Tentando listar pacientes...");
            
            // Buscar pacientes com estratégia de fetch adequada
            List<Paciente> pacientes = pacienteRepository.findByAtivoTrue();
            System.out.println("✅ Encontrados " + pacientes.size() + " pacientes");
            
            return ResponseEntity.ok(pacientes);
        } catch (Exception e) {
            System.err.println("❌ Erro ao listar pacientes: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ArrayList<>()); // Retornar lista vazia em caso de erro
        }
    }    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Long id) {
        try {
            Paciente paciente = pacienteService.buscarPeloCodigo(id);
            if (paciente != null) {
                return ResponseEntity.ok(paciente);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar paciente: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Paciente> criarPaciente(@RequestBody Paciente paciente, @RequestHeader("X-Usuario-Email") String emailUsuario) {
        try {
            System.out.println("🆕 Criando paciente: " + paciente.getNome());
            
            // Remove o ID para garantir que seja um novo registro
            paciente.setId(null);
            
            // Associar ao usuário logado (via header)
            Usuario usuario = usuarioService.buscarPorEmail(emailUsuario);
            if (usuario == null) {
                System.err.println("⚠️ Usuário não autenticado ou não encontrado: " + emailUsuario);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            paciente.setUsuarioResponsavel(usuario);
            
            System.out.println("💾 Salvando paciente...");
            Paciente pacienteSalvo = pacienteService.salvar(paciente);
            System.out.println("✅ Paciente salvo com ID: " + pacienteSalvo.getId());
            
            return ResponseEntity.status(HttpStatus.CREATED).body(pacienteSalvo);
        } catch (Exception e) {
            System.err.println("❌ Erro ao salvar paciente: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> atualizarPaciente(@PathVariable Long id, @RequestBody Paciente paciente, @RequestHeader("X-Usuario-Email") String emailUsuario) {
        try {
            // Buscar o usuário pelo email
            Usuario usuario = usuarioService.buscarPorEmail(emailUsuario);
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Verificar se o paciente existe e pertence ao usuário
            if (!pacienteRepository.existsByIdAndUsuarioResponsavelId(id, usuario.getId())) {
                return ResponseEntity.notFound().build();
            }

            Paciente pacienteExistente = pacienteService.buscarPeloCodigo(id);
            if (pacienteExistente != null) {
                // Preservar dados existentes e atualizar apenas os campos fornecidos
                pacienteExistente.setNome(paciente.getNome());
                pacienteExistente.setDataNascimento(paciente.getDataNascimento());
                pacienteExistente.setGenero(paciente.getGenero());
                pacienteExistente.setRelacionamento(paciente.getRelacionamento());
                pacienteExistente.setTelefone(paciente.getTelefone());
                
                // Atualizar contato de emergência se fornecido
                if (paciente.getContatoEmergencial() != null) {
                    if (pacienteExistente.getContatoEmergencial() != null) {
                        var contato = pacienteExistente.getContatoEmergencial();
                        contato.setNome(paciente.getContatoEmergencial().getNome());
                        contato.setTelefone(paciente.getContatoEmergencial().getTelefone());
                        contato.setEmail(paciente.getContatoEmergencial().getEmail());
                        contato.setInstagram(paciente.getContatoEmergencial().getInstagram());
                    } else {
                        pacienteExistente.setContatoEmergencial(paciente.getContatoEmergencial());
                    }
                }
                
                // Atualizar informações médicas se fornecidas
                if (paciente.getInformacaoMedica() != null) {
                    if (pacienteExistente.getInformacaoMedica() != null) {
                        var info = pacienteExistente.getInformacaoMedica();
                        info.setTipoSangue(paciente.getInformacaoMedica().getTipoSangue());
                        info.setDeficiencia(paciente.getInformacaoMedica().getDeficiencia());
                        info.setProblemasEspecificos(paciente.getInformacaoMedica().getProblemasEspecificos());
                    } else {
                        pacienteExistente.setInformacaoMedica(paciente.getInformacaoMedica());
                    }
                }
                
                // Atualizar sinais vitais se fornecidos (mas preservar existentes se não fornecidos)
                if (paciente.getSinaisVitais() != null) {
                    if (pacienteExistente.getSinaisVitais() != null) {
                        var sinais = pacienteExistente.getSinaisVitais();
                        if (paciente.getSinaisVitais().getOxigenio() != null) {
                            sinais.setOxigenio(paciente.getSinaisVitais().getOxigenio());
                        }
                        if (paciente.getSinaisVitais().getTemperatura() != null) {
                            sinais.setTemperatura(paciente.getSinaisVitais().getTemperatura());
                        }
                        if (paciente.getSinaisVitais().getBatimentos() != null) {
                            sinais.setBatimentos(paciente.getSinaisVitais().getBatimentos());
                        }
                        // Manter status existentes ou usar os novos
                        if (paciente.getSinaisVitais().getStatusOxigenio() != null) {
                            sinais.setStatusOxigenio(paciente.getSinaisVitais().getStatusOxigenio());
                        }
                        if (paciente.getSinaisVitais().getStatusTemperatura() != null) {
                            sinais.setStatusTemperatura(paciente.getSinaisVitais().getStatusTemperatura());
                        }
                        if (paciente.getSinaisVitais().getStatusBatimentos() != null) {
                            sinais.setStatusBatimentos(paciente.getSinaisVitais().getStatusBatimentos());
                        }
                    } else {
                        pacienteExistente.setSinaisVitais(paciente.getSinaisVitais());
                    }
                }
                
                Paciente pacienteAtualizado = pacienteService.salvar(pacienteExistente);
                return ResponseEntity.ok(pacienteAtualizado);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.err.println("Erro ao atualizar paciente: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPaciente(@PathVariable Long id, @RequestHeader("X-Usuario-Email") String emailUsuario) {
        try {
            // Buscar o usuário pelo email
            Usuario usuario = usuarioService.buscarPorEmail(emailUsuario);
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Verificar se o paciente existe e pertence ao usuário
            if (!pacienteRepository.existsByIdAndUsuarioResponsavelId(id, usuario.getId())) {
                return ResponseEntity.notFound().build();
            }

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
    public ResponseEntity<Long> contarPacientes(@RequestHeader("X-Usuario-Email") String emailUsuario) {
        try {
            // Buscar o usuário pelo email
            Usuario usuario = usuarioService.buscarPorEmail(emailUsuario);
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Contar apenas os pacientes do usuário logado
            List<Paciente> pacientes = pacienteRepository.findByUsuarioResponsavel(usuario);
            return ResponseEntity.ok((long) pacientes.size());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}/historico-sinais/{periodo}")
    public ResponseEntity<?> obterHistoricoSinaisVitais(@PathVariable Long id, @PathVariable String periodo, @RequestHeader("X-Usuario-Email") String emailUsuario) {
        try {
            // Buscar o usuário pelo email
            Usuario usuario = usuarioService.buscarPorEmail(emailUsuario);
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Verificar se o paciente existe e pertence ao usuário
            if (!pacienteRepository.existsByIdAndUsuarioResponsavelId(id, usuario.getId())) {
                return ResponseEntity.notFound().build();
            }

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