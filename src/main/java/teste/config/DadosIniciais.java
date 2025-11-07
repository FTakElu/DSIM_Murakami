package teste.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import teste.model.Usuario;
import teste.repository.UsuarioRepository;

/**
 * Configuração de dados iniciais LIMPOS para PostgreSQL
 * Cria apenas o usuário administrador essencial
 */
@Component
public class DadosIniciais implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Criar apenas usuário administrador essencial
        if (usuarioRepository.count() == 0) {
            Usuario admin = new Usuario();
            admin.setNome("Administrador DSIM");
            admin.setEmail("admin@dsim.com");
            admin.setSenha(passwordEncoder.encode("admin123"));
            admin.setAtivo(true);
            usuarioRepository.save(admin);
            System.out.println("✅ USUÁRIO ADMIN CRIADO: admin@dsim.com / admin123");
            System.out.println("🐘 Banco PostgreSQL inicializado com dados limpos");
        }
    }
}