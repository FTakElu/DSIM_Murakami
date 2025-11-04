package teste.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import teste.model.Usuario;
import teste.repository.UsuarioRepository;

@Service
@Transactional
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    public Usuario salvar(Usuario usuario) {
        // Criptografar senha apenas se não estiver já criptografada
        if (usuario.getSenha() != null && !usuario.getSenha().startsWith("$2a$")) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }
        return usuarioRepository.save(usuario);
    }
    
    public Usuario cadastrarUsuario(String nome, String email, String senha) {
        // Verificar se email já existe
        if (usuarioRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email já cadastrado no sistema");
        }
        
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setAtivo(true);
        
        return salvar(usuario);
    }
    
    public void desativarUsuario(Long id) {
        Usuario usuario = buscarPorId(id);
        if (usuario != null) {
            usuario.setAtivo(false);
            usuarioRepository.save(usuario);
        }
    }
    
    public void ativarUsuario(Long id) {
        Usuario usuario = buscarPorId(id);
        if (usuario != null) {
            usuario.setAtivo(true);
            usuarioRepository.save(usuario);
        }
    }
    
    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<Usuario> buscarAtivos() {
        return usuarioRepository.findByAtivoTrue();
    }
    
    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElse(null);
    }
    
    @Transactional(readOnly = true)
    public Usuario buscarPorEmail(String email) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        return usuario.orElse(null);
    }
    
    @Transactional(readOnly = true)
    public List<Usuario> buscarPorNome(String nome) {
        return usuarioRepository.findByNomeContaining(nome);
    }
    
    @Transactional(readOnly = true)
    public boolean emailJaExiste(String email) {
        return usuarioRepository.existsByEmail(email);
    }
    
    @Transactional(readOnly = true)
    public boolean emailExiste(String email) {
        return usuarioRepository.existsByEmail(email);
    }
    
    public boolean validarCredenciais(String email, String senha) {
        Usuario usuario = buscarPorEmail(email);
        if (usuario != null && usuario.getAtivo()) {
            return passwordEncoder.matches(senha, usuario.getSenha());
        }
        return false;
    }
    
    public void excluirPermanentemente(Long id) {
        usuarioRepository.deleteById(id);
    }
}