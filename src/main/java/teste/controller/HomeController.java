package teste.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/index.xhtml";
    }
    
    @GetMapping("/login")
    public String login() {
        return "redirect:/pages/login.xhtml";
    }
    
    @GetMapping("/cadastro")
    public String cadastro() {
        return "redirect:/pages/cadastro.xhtml";
    }
    
    @GetMapping("/admin")
    public String admin() {
        return "redirect:/pages/admin.xhtml";
    }
    
    @GetMapping("/dashboard")
    public String dashboard() {
        return "redirect:/pages/dashboard.xhtml";
    }
    
    @GetMapping("/pacientes")
    public String pacientes() {
        return "redirect:/pages/pacientes.xhtml";
    }
    
    @GetMapping("/pacientes/adicionar")
    public String adicionarPaciente() {
        return "redirect:/pages/cadastrar-paciente.xhtml";
    }
    
    @GetMapping("/pacientes/detalhes")
    public String detalhesPaciente() {
        return "redirect:/pages/detalhes-paciente.xhtml";
    }
    
    @GetMapping("/configurar-alarme")
    public String configurarAlarme() {
        return "redirect:/pages/configurar-alarme.xhtml";
    }
}