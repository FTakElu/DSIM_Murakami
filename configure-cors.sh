#!/bin/bash
# Script para configurar CORS no backend EC2

echo "🔧 Configurando CORS no backend..."

# Ir para diretório do projeto
cd /opt/dsim

# Backup do arquivo atual
sudo cp src/main/java/teste/config/WebConfig.java src/main/java/teste/config/WebConfig.java.bak

# Criar nova configuração CORS mais permissiva
sudo tee src/main/java/teste/config/WebConfig.java > /dev/null << 'EOF'
package teste.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/", "classpath:/webapp/", "classpath:/webapp/view/");
    }
}
EOF

echo "✅ Arquivo WebConfig.java atualizado"

# Recompilar aplicação
echo "🔨 Recompilando aplicação..."
mvn clean package -DskipTests

if [ $? -eq 0 ]; then
    echo "✅ Compilação bem-sucedida"
    
    # Reiniciar serviço
    echo "🔄 Reiniciando serviço..."
    sudo systemctl restart dsim
    
    # Aguardar reinicialização
    sleep 5
    
    # Verificar status
    sudo systemctl status dsim --no-pager
    
    echo "🎉 CORS configurado! Teste novamente o frontend."
else
    echo "❌ Erro na compilação"
    
    # Restaurar backup se deu erro
    sudo cp src/main/java/teste/config/WebConfig.java.bak src/main/java/teste/config/WebConfig.java
    echo "🔄 Backup restaurado"
fi