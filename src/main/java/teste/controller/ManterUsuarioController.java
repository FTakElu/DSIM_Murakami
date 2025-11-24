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

import teste.model.Usuario;
import teste.service.ManterUsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class ManterUsuarioController {
    
    @Autowired
    private ManterUsuarioService usuarioService;
    
    /**
     * Endpoint para login de usuário
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciais) {
        try {
            String email = credenciais.get("email");
            String senha = credenciais.get("senha");
            
            if (email == null || senha == null) {
                return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "Email e senha são obrigatórios"));
            }
            
            Usuario usuario = usuarioService.buscarPorEmail(email);
            
            if (usuario == null) {
                return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "Email não cadastrado"));
            }
            
            if (!usuario.getAtivo()) {
                return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "Usuário desativado"));
            }
            
            boolean credenciaisValidas = usuarioService.validarCredenciais(email, senha);
            
            if (credenciaisValidas) {
                // Senha removida automaticamente por Jackson (@JsonProperty WRITE_ONLY)
                return ResponseEntity.ok(Map.of(
                    "success", true, 
                    "message", "Login realizado com sucesso",
                    "usuario", usuario
                ));
            } else {
                return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "Senha incorreta"));
            }
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(Map.of("success", false, "message", "Erro interno do servidor"));
        }
    }
    
    /**
     * Endpoint para cadastro de novo usuário
     */
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody Map<String, String> dados) {
        try {
            String nome = dados.get("nome");
            String email = dados.get("email");
            String senha = dados.get("senha");
            
            if (nome == null || email == null || senha == null) {
                return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "Todos os campos são obrigatórios"));
            }
            
            Usuario usuario = usuarioService.cadastrarUsuario(nome, email, senha);
            
            // Senha removida automaticamente por Jackson (@JsonProperty WRITE_ONLY)
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Usuário cadastrado com sucesso",
                "usuario", usuario
            ));
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(Map.of("success", false, "message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(Map.of("success", false, "message", "Erro interno do servidor"));
        }
    }
    
    /**
     * Listar todos os usuários (apenas para admins - temporário)
     */
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.buscarTodos();
        // Senha removida automaticamente por Jackson (@JsonProperty WRITE_ONLY)
        return ResponseEntity.ok(usuarios);
    }
    
    /**
     * Buscar dados do usuário logado por email
     */
    @GetMapping("/perfil")
    public ResponseEntity<?> buscarPerfil(@RequestParam String email) {
        try {
            Usuario usuario = usuarioService.buscarPorEmail(email);
            if (usuario == null) {
                return ResponseEntity.notFound().build();
            }
            // Senha removida automaticamente por Jackson (@JsonProperty WRITE_ONLY)
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("success", false, "message", "Erro ao buscar perfil"));
        }
    }
    
    /**
     * Listar apenas usuários ativos
     */
    @GetMapping("/ativos")
    public ResponseEntity<List<Usuario>> listarUsuariosAtivos() {
        List<Usuario> usuarios = usuarioService.buscarAtivos();
        // Senha removida automaticamente por Jackson (@JsonProperty WRITE_ONLY)
        return ResponseEntity.ok(usuarios);
    }
    
    /**
     * Buscar usuário por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        // Senha removida automaticamente por Jackson (@JsonProperty WRITE_ONLY)
        return ResponseEntity.ok(usuario);
    }
    

    
    /**
     * Buscar usuários por nome
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam String nome) {
        List<Usuario> usuarios = usuarioService.buscarPorNome(nome);
        // Senha removida automaticamente por Jackson (@JsonProperty WRITE_ONLY)
        return ResponseEntity.ok(usuarios);
    }
    
    /**
     * Atualizar usuário
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarUsuario(@PathVariable Long id, @RequestBody Map<String, String> dados) {
        try {
            Usuario usuarioExistente = usuarioService.buscarPorId(id);
            if (usuarioExistente == null) {
                return ResponseEntity.notFound().build();
            }
            
            if (dados.containsKey("nome")) {
                usuarioExistente.setNome(dados.get("nome"));
            }
            
            if (dados.containsKey("email")) {
                String novoEmail = dados.get("email");
                // Verificar se novo email já existe (exceto o próprio usuário)
                Usuario usuarioComEmail = usuarioService.buscarPorEmail(novoEmail);
                if (usuarioComEmail != null && !usuarioComEmail.getId().equals(id)) {
                    return ResponseEntity.badRequest()
                        .body(Map.of("success", false, "message", "Email já cadastrado"));
                }
                usuarioExistente.setEmail(novoEmail);
            }
            
            if (dados.containsKey("senha")) {
                usuarioExistente.setSenha(dados.get("senha"));
            }
            
            Usuario usuarioAtualizado = usuarioService.salvar(usuarioExistente);
            // Senha removida automaticamente por Jackson (@JsonProperty WRITE_ONLY)
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Usuário atualizado com sucesso",
                "usuario", usuarioAtualizado
            ));
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(Map.of("success", false, "message", "Erro interno do servidor"));
        }
    }
    
    /**
     * Desativar usuário
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> desativarUsuario(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.buscarPorId(id);
            if (usuario == null) {
                return ResponseEntity.notFound().build();
            }
            
            usuarioService.desativarUsuario(id);
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Usuário desativado com sucesso"
            ));
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(Map.of("success", false, "message", "Erro interno do servidor"));
        }
    }
    
    /**
     * Ativar usuário
     */
    @PutMapping("/{id}/ativar")
    public ResponseEntity<?> ativarUsuario(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.buscarPorId(id);
            if (usuario == null) {
                return ResponseEntity.notFound().build();
            }
            
            usuarioService.ativarUsuario(id);
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Usuário ativado com sucesso"
            ));
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(Map.of("success", false, "message", "Erro interno do servidor"));
        }
    }
    
    /**
     * Verificar se email já existe
     */
    @GetMapping("/verificar-email")
    public ResponseEntity<Map<String, Boolean>> verificarEmail(@RequestParam String email) {
        boolean existe = usuarioService.emailJaExiste(email);
        return ResponseEntity.ok(Map.of("existe", existe));
    }
    
    /**
     * Excluir usuário permanentemente
     */
    @DeleteMapping("/{id}/excluir")
    public ResponseEntity<?> excluirUsuario(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.buscarPorId(id);
            if (usuario == null) {
                return ResponseEntity.notFound().build();
            }
            
            usuarioService.excluirPermanentemente(id);
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Usuário excluído permanentemente com sucesso"
            ));
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(Map.of("success", false, "message", "Erro interno do servidor"));
        }
    }
}