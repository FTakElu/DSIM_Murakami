package teste.config;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import teste.model.Alerta;
import teste.model.ContatoEmergencial;
import teste.model.InformacaoMedica;
import teste.model.Paciente;
import teste.model.SinaisVitais;
import teste.model.Usuario;
import teste.repository.AlertaRepository;
import teste.repository.PacienteRepository;
import teste.repository.UsuarioRepository;

@Component
public class DadosIniciais implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PacienteRepository pacienteRepository;
    
    @Autowired
    private AlertaRepository alertaRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Usuario admin = null;
        
        if (usuarioRepository.count() == 0) {
            // Criar usuário administrativo usando o UsuarioService
            admin = new Usuario();
            admin.setNome("Administrador Sistema");
            admin.setEmail("admin@sistema.com");
            admin.setSenha(passwordEncoder.encode("admin123")); // Criptografar diretamente aqui
            admin.setAtivo(true);
            admin = usuarioRepository.save(admin);
            System.out.println("*** USUÁRIO ADMIN CRIADO: admin@sistema.com / admin123 ***");
        } else {
            // Buscar o usuário admin existente
            admin = usuarioRepository.findByEmail("admin@sistema.com").orElse(null);
            
            // Se o admin existe mas pode ter senha incorreta, vamos atualizá-la
            if (admin != null) {
                admin.setSenha(passwordEncoder.encode("admin123")); // Criptografar diretamente
                admin = usuarioRepository.save(admin);
                System.out.println("*** SENHA DO ADMIN ATUALIZADA: admin@sistema.com / admin123 ***");
            }
        }
        
        if (pacienteRepository.count() == 0) {
            // Criar pacientes de exemplo
            
            // Paciente 1
            ContatoEmergencial contato1 = new ContatoEmergencial();
            contato1.setNome("Ana Silva");
            contato1.setTelefone("(11) 98765-4321");
            contato1.setEmail("ana@email.com");
            contato1.setInstagram("@ana_silva");
            
            InformacaoMedica info1 = new InformacaoMedica();
            info1.setTipoSangue("O+");
            info1.setDeficiencia("Nenhuma");
            info1.setProblemasEspecificos("Diabetes, Hipertensão");
            
            SinaisVitais sinais1 = new SinaisVitais();
            sinais1.setOxigenio(98.5);
            sinais1.setStatusOxigenio("stable");
            sinais1.setTemperatura(36.8);
            sinais1.setStatusTemperatura("stable");
            sinais1.setBatimentos(75);
            sinais1.setStatusBatimentos("stable");
            
            Paciente paciente1 = new Paciente();
            paciente1.setNome("Carlos Eduardo Silva");
            paciente1.setDataNascimento(LocalDate.of(1985, 3, 15));
            paciente1.setGenero("Homem");
            paciente1.setRelacionamento("Casado(a)");
            paciente1.setTelefone("(11) 99999-8888");
            paciente1.setImageUrl("/assets/carlos-eduardo.jpg");
            paciente1.setContatoEmergencial(contato1);
            paciente1.setInformacaoMedica(info1);
            paciente1.setSinaisVitais(sinais1);
            paciente1.setUsuarioResponsavel(admin);
            
            pacienteRepository.save(paciente1);
            
            // Paciente 2
            ContatoEmergencial contato2 = new ContatoEmergencial();
            contato2.setNome("Pedro Santos");
            contato2.setTelefone("(11) 95555-1234");
            contato2.setEmail("pedro@email.com");
            contato2.setInstagram("@pedro_santos");
            
            InformacaoMedica info2 = new InformacaoMedica();
            info2.setTipoSangue("A+");
            info2.setDeficiencia("Visual leve");
            info2.setProblemasEspecificos("Nenhum");
            
            SinaisVitais sinais2 = new SinaisVitais();
            sinais2.setOxigenio(95.2);
            sinais2.setStatusOxigenio("warning");
            sinais2.setTemperatura(37.2);
            sinais2.setStatusTemperatura("warning");
            sinais2.setBatimentos(90);
            sinais2.setStatusBatimentos("warning");
            
            Paciente paciente2 = new Paciente();
            paciente2.setNome("Márcia dos Santos");
            paciente2.setDataNascimento(LocalDate.of(1992, 7, 22));
            paciente2.setGenero("Mulher");
            paciente2.setRelacionamento("Solteiro(a)");
            paciente2.setTelefone("(11) 88888-7777");
            paciente2.setImageUrl("/assets/marcia-dos-santos.png");
            paciente2.setContatoEmergencial(contato2);
            paciente2.setInformacaoMedica(info2);
            paciente2.setSinaisVitais(sinais2);
            paciente2.setUsuarioResponsavel(admin);
            
            pacienteRepository.save(paciente2);
            
            // Criar alertas de exemplo
            Alerta alerta1 = new Alerta();
            alerta1.setPaciente(paciente2);
            alerta1.setTipo(Alerta.TipoAlerta.OXIGENIO_BAIXO);
            alerta1.setMensagem("Oxigenação baixa detectada: 95.2%. Paciente: Márcia dos Santos");
            alerta1.setPrioridade(Alerta.NivelPrioridade.ALTA);
            alerta1.setLido(false);
            alerta1.setAtivo(true);
            
            alertaRepository.save(alerta1);
            
            Alerta alerta2 = new Alerta();
            alerta2.setPaciente(paciente2);
            alerta2.setTipo(Alerta.TipoAlerta.TEMPERATURA_ALTA);
            alerta2.setMensagem("Temperatura elevada detectada: 37.2°C. Paciente: Márcia dos Santos");
            alerta2.setPrioridade(Alerta.NivelPrioridade.MEDIA);
            alerta2.setLido(false);
            alerta2.setAtivo(true);
            
            alertaRepository.save(alerta2);
        }
    }
}