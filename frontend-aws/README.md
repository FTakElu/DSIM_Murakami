# 🚀 Deploy DSIM na AWS - Guia Completo

## 📋 Arquitetura do Deploy
- **Frontend**: AWS Amplify (Hospedagem estática)
- **Backend**: AWS EC2 (Spring Boot)
- **Banco**: AWS RDS (PostgreSQL/MySQL)

---

## 🎯 PARTE 1: Deploy do Frontend (AWS Amplify)

### 1. Preparar Repositório GitHub
```bash
# Na pasta frontend-aws
git init
git add .
git commit -m "Frontend DSIM para AWS Amplify"
git branch -M main
git remote add origin https://github.com/SEU_USUARIO/dsim-frontend.git
git push -u origin main
```

### 2. Configurar AWS Amplify
1. **Acesse**: https://console.aws.amazon.com/amplify/
2. **New app** → **Host web app**
3. **GitHub** como source provider
4. **Autorize** e selecione o repositório `dsim-frontend`
5. **Branch**: main
6. **Build settings**: Amplify detectará o `amplify.yml` automaticamente
7. **Deploy**

### 3. Após Deploy do Frontend
- Anote a URL gerada (ex: `https://main.d1234567890.amplifyapp.com`)

---

## 🖥️ PARTE 2: Deploy do Backend (AWS EC2)

### 1. Criar Instância EC2
1. **Acesse**: https://console.aws.amazon.com/ec2/
2. **Launch Instance**
3. **Configurações**:
   - **AMI**: Amazon Linux 2023
   - **Instance Type**: t3.micro (free tier) ou t3.small
   - **Key Pair**: Crie um novo ou use existente
   - **Security Group**: Libere portas 22 (SSH), 80 (HTTP), 8080 (Spring Boot)

### 2. Conectar à Instância
```bash
ssh -i "sua-chave.pem" ec2-user@SEU-IP-PUBLICO
```

### 3. Configurar Servidor
```bash
# Atualizar sistema
sudo yum update -y

# Instalar Java 21
sudo yum install -y java-21-amazon-corretto

# Verificar instalação
java -version

# Instalar Git
sudo yum install -y git
```

### 4. Deploy da Aplicação
```bash
# Clonar repositório do backend
git clone https://github.com/SEU_USUARIO/dsim-backend.git
cd dsim-backend

# Compilar aplicação
./mvnw clean package -DskipTests

# Executar aplicação
nohup java -jar target/sistema-monitoramento-pacientes-1.0.0-SNAPSHOT.jar > app.log 2>&1 &
```

### 5. Configurar como Serviço (Opcional)
```bash
# Criar arquivo de serviço
sudo nano /etc/systemd/system/dsim.service
```

Conteúdo do arquivo:
```ini
[Unit]
Description=DSIM Spring Boot Application
After=network.target

[Service]
Type=simple
User=ec2-user
ExecStart=/usr/bin/java -jar /home/ec2-user/dsim-backend/target/sistema-monitoramento-pacientes-1.0.0-SNAPSHOT.jar
Restart=always

[Install]
WantedBy=multi-user.target
```

```bash
# Habilitar serviço
sudo systemctl daemon-reload
sudo systemctl enable dsim
sudo systemctl start dsim
```

---

## 🗄️ PARTE 3: Migrar Banco para RDS

### 1. Criar Instância RDS
1. **Acesse**: https://console.aws.amazon.com/rds/
2. **Create database**
3. **Configurações**:
   - **Engine**: PostgreSQL ou MySQL
   - **Template**: Free tier
   - **DB Instance Identifier**: dsim-database
   - **Master username**: admin
   - **Master password**: [senha segura]
   - **VPC**: Mesma do EC2
   - **Security Group**: Permitir conexão do EC2 (porta 5432/3306)

### 2. Atualizar Configuração do Backend

No EC2, edite o `application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://dsim-database.XXXXXX.us-east-1.rds.amazonaws.com:5432/dsim
    username: admin
    password: SUA_SENHA_RDS
    driver-class-name: org.postgresql.Driver
  
  jpa:
    hibernate:
      ddl-auto: update
    database-platform: org.hibernate.dialect.PostgreSQLDialect
```

### 3. Migrar Dados (Se Necessário)
```bash
# Exportar dados do H2 (se houver)
# Importar para RDS usando ferramentas específicas do banco
```

---

## 🔗 PARTE 4: Conectar Frontend e Backend

### 1. Atualizar Configuração da API
No frontend implantado, edite `js/api-config.js`:
```javascript
const API_CONFIG = {
    BASE_URL: 'http://SEU-IP-EC2:8080', // IP público do EC2
    // ... resto da configuração
};
```

### 2. Configurar CORS no Backend
No Spring Boot, adicione:
```java
@CrossOrigin(origins = "https://main.d1234567890.amplifyapp.com") // URL do Amplify
```

---

## 🔒 PARTE 5: Configurações de Segurança

### 1. Security Groups
- **EC2**: Liberar porta 8080 apenas para o Amplify
- **RDS**: Liberar porta do banco apenas para o EC2

### 2. HTTPS (Opcional)
- Configure um domínio personalizado no Amplify
- Use Certificate Manager para SSL gratuito

---

## 📊 PARTE 6: Monitoramento

### 1. CloudWatch
- Configure logs do EC2
- Monitore CPU, memória e rede

### 2. Health Checks
- Configure health checks no Application Load Balancer (se usar)

---

## 💰 Estimativa de Custos (Free Tier)
- **Amplify**: Grátis até 1GB de dados
- **EC2 t3.micro**: Grátis por 12 meses
- **RDS t3.micro**: Grátis por 12 meses
- **Data Transfer**: Primeiros 15GB grátis

---

## 🚨 Checklist de Deploy

### Frontend ✅
- [ ] Repositório GitHub criado
- [ ] Amplify configurado e funcionando
- [ ] URL do frontend anotada

### Backend ✅
- [ ] EC2 criada e configurada
- [ ] Java 21 instalado
- [ ] Aplicação rodando na porta 8080
- [ ] Security Group configurado

### Banco ✅
- [ ] RDS criado
- [ ] Configuração atualizada no backend
- [ ] Conexão testada

### Integração ✅
- [ ] CORS configurado
- [ ] API_CONFIG atualizada no frontend
- [ ] Comunicação frontend-backend funcionando

---

## 🔧 Comandos Úteis

### Verificar status da aplicação no EC2:
```bash
sudo systemctl status dsim
tail -f app.log
```

### Reiniciar aplicação:
```bash
sudo systemctl restart dsim
```

### Verificar conectividade do banco:
```bash
telnet SEU-RDS-ENDPOINT 5432
```

Quer que eu ajude com algum passo específico desse processo?