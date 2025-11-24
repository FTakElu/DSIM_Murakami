package teste.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/api/**")
        .allowedOriginPatterns("https://*.amplifyapp.com", "http://3.237.34.95", "https://3.237.34.95", "http://localhost:*", "http://127.0.0.1:*")
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        .allowedHeaders("*")
        .allowCredentials(true)
        .maxAge(3600);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve arquivos HTML do diretório VIEW (seguindo padrão MVC)
        registry.addResourceHandler("/**")
                .addResourceLocations(
                    "/view/",
                    "classpath:/static/",
                    "classpath:/public/"
                )
                .setCachePeriod(0); // Desabilita cache durante desenvolvimento
        
        // Serve recursos específicos (CSS, JS, imagens)
        registry.addResourceHandler("/assets/**")
                .addResourceLocations("/view/assets/");
                
        registry.addResourceHandler("/css/**")
                .addResourceLocations("/view/css/");
    }
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Redirecionar a raiz para a página inicial
        registry.addViewController("/").setViewName("forward:/index.html");
        registry.addViewController("/index").setViewName("forward:/index.html");
        
        // Páginas de autenticação
        registry.addViewController("/login").setViewName("forward:/pages/login.html");
        registry.addViewController("/cadastro").setViewName("forward:/pages/cadastro.html");
        
        // Páginas de pacientes
        registry.addViewController("/pacientes").setViewName("forward:/pages/pacientes.html");
        registry.addViewController("/adicionar-paciente").setViewName("forward:/pages/adicionar-paciente.html");
        registry.addViewController("/detalhes-paciente").setViewName("forward:/pages/detalhes-paciente.html");
        
        // Páginas de usuários
        registry.addViewController("/usuarios").setViewName("forward:/pages/usuarios.html");
        registry.addViewController("/editar-usuario").setViewName("forward:/pages/editar-usuario.html");
        
        // Páginas de configuração
        registry.addViewController("/configurar-alertas").setViewName("forward:/pages/configurar-alertas.html");
    }
}