package teste.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/index.html";
    }
    
    @GetMapping("/login")
    public String login() {
        return "redirect:/pages/login.html";
    }
    
    @GetMapping("/cadastro")
    public String cadastro() {
        return "redirect:/pages/cadastro.html";
    }
    
    @GetMapping("/dashboard")
    public String dashboard() {
        return "redirect:/index.html";
    }
    
    @GetMapping("/pacientes")
    public String pacientes() {
        return "redirect:/pages/pacientes.html";
    }
    
    @GetMapping("/pacientes/adicionar")
    public String adicionarPaciente() {
        return "redirect:/pages/adicionar-paciente.html";
    }
    
    @GetMapping("/pacientes/detalhes")
    public String detalhesPaciente() {
        return "redirect:/pages/detalhes-paciente.html";
    }
    
    @GetMapping("/configurar-alertas")
    public String configurarAlertas() {
        return "redirect:/pages/configurar-alertas.html";
    }
    
    @GetMapping("/usuarios")
    public String usuarios() {
        return "redirect:/pages/usuarios.html";
    }
    
    @GetMapping("/usuarios/editar")
    public String editarUsuario() {
        return "redirect:/pages/editar-usuario.html";
    }
}