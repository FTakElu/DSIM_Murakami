# 🚀 DSIM Murakami - Guia Completo de Setup AWS

Este guia contém todos os passos para recriar a infraestrutura AWS do projeto **dsim_murakami** do zero.

## 📋 Índice

1. [Pré-requisitos](#pré-requisitos)
2. [Arquitetura do Projeto](#arquitetura-do-projeto)
3. [Setup Automatizado](#setup-automatizado)
4. [Setup Manual (Passo a Passo)](#setup-manual-passo-a-passo)
5. [Configuração do Projeto](#configuração-do-projeto)
6. [Troubleshooting](#troubleshooting)

---

## 🎯 Pré-requisitos

### Ferramentas Necessárias

- **AWS CLI** instalado e configurado
  ```bash
  aws --version
  aws configure
  ```

- **Git Bash** (Windows) ou terminal bash (Linux/Mac)

- **Conta AWS** ativa com permissões para:
  - RDS (PostgreSQL)
  - EC2
  - AWS Amplify
  - VPC e Security Groups

### Custos Estimados

- **RDS PostgreSQL (db.t3.micro)**: ~$15/mês
- **EC2 (t3.micro)**: Grátis (Free Tier) ou ~$10/mês
- **Amplify**: Grátis para pequenos projetos
- **Total estimado**: $15-25/mês

---

## 🏗️ Arquitetura do Projeto

```
┌─────────────────┐
│  AWS Amplify    │  Frontend (HTML/CSS/JS)
│  (Frontend)     │  https://dsim-murakami.amplifyapp.com
└────────┬────────┘
         │ HTTPS
         ▼
┌─────────────────┐
│   EC2 Instance  │  Backend Spring Boot + NGINX
│  (t3.micro)     │  - Java 21
│                 │  - Maven
│   NGINX Proxy   │  - NGINX (portas 80/443)
│   Spring Boot   │  https://<PUBLIC_IP>
└────────┬────────┘
         │ JDBC
         ▼
┌─────────────────┐
│  RDS PostgreSQL │  Banco de Dados
│  (db.t3.micro)  │  - PostgreSQL 15.15
│                 │  - 20GB SSD
└─────────────────┘
```

---

## ⚡ Setup Automatizado

### Opção 1: Linux/Mac/Git Bash (Recomendado)

```bash
cd aws-setup

# 1. Configurar RDS PostgreSQL
chmod +x 01-setup-rds.sh
./01-setup-rds.sh

# 2. Configurar EC2 Instance
chmod +x 02-setup-ec2.sh
./02-setup-ec2.sh

# 3. Deploy do Backend
chmod +x 03-deploy-backend.sh
./03-deploy-backend.sh

# 4. Configurar Amplify (instruções no terminal)
chmod +x 04-setup-amplify.sh
./04-setup-amplify.sh
```

### Opção 2: Windows PowerShell

```powershell
cd aws-setup
.\setup-aws-windows.ps1
```

**Nota**: O script PowerShell fornece um guia, mas os scripts bash têm automação completa.

---

## 📖 Setup Manual (Passo a Passo)

### Passo 1: Criar RDS PostgreSQL

#### 1.1. Criar Security Group para RDS

```bash
# Obter ID da VPC padrão
VPC_ID=$(aws ec2 describe-vpcs --region us-east-1 \
  --filters "Name=isDefault,Values=true" \
  --query "Vpcs[0].VpcId" --output text)

# Criar Security Group
SG_ID=$(aws ec2 create-security-group \
  --region us-east-1 \
  --group-name "dsim-murakami-rds-sg" \
  --description "Security group para RDS do projeto dsim_murakami" \
  --vpc-id "$VPC_ID" \
  --query 'GroupId' --output text)

# Permitir acesso PostgreSQL (porta 5432)
aws ec2 authorize-security-group-ingress \
  --region us-east-1 \
  --group-id "$SG_ID" \
  --protocol tcp \
  --port 5432 \
  --cidr 0.0.0.0/0
```

#### 1.2. Criar Instância RDS

```bash
aws rds create-db-instance \
  --region us-east-1 \
  --db-instance-identifier "dsim-murakami-db" \
  --db-name "dsim_production" \
  --db-instance-class "db.t3.micro" \
  --engine "postgres" \
  --engine-version "15.15" \
  --master-username "dsim_admin" \
  --master-user-password "SuaSenhaSegura123#" \
  --allocated-storage 20 \
  --vpc-security-group-ids "$SG_ID" \
  --backup-retention-period 7 \
  --publicly-accessible \
  --storage-type gp2 \
  --tags Key=Name,Value=dsim-murakami Key=Project,Value=dsim_murakami
```

#### 1.3. Aguardar e Obter Endpoint

```bash
# Aguardar instância ficar disponível (5-10 minutos)
aws rds wait db-instance-available \
  --region us-east-1 \
  --db-instance-identifier "dsim-murakami-db"

# Obter endpoint
DB_ENDPOINT=$(aws rds describe-db-instances \
  --region us-east-1 \
  --db-instance-identifier "dsim-murakami-db" \
  --query "DBInstances[0].Endpoint.Address" \
  --output text)

echo "RDS Endpoint: $DB_ENDPOINT"
```

---

### Passo 2: Criar EC2 Instance

#### 2.1. Criar Par de Chaves

```bash
aws ec2 create-key-pair \
  --region us-east-1 \
  --key-name "dsim-murakami-keypair" \
  --query 'KeyMaterial' \
  --output text > dsim-murakami-keypair.pem

chmod 400 dsim-murakami-keypair.pem
```

#### 2.2. Criar Security Group para EC2

```bash
SG_EC2=$(aws ec2 create-security-group \
  --region us-east-1 \
  --group-name "dsim-murakami-ec2-sg" \
  --description "Security group para EC2 do projeto dsim_murakami" \
  --vpc-id "$VPC_ID" \
  --query 'GroupId' --output text)

# SSH (22)
aws ec2 authorize-security-group-ingress \
  --region us-east-1 --group-id "$SG_EC2" \
  --protocol tcp --port 22 --cidr 0.0.0.0/0

# HTTP (80)
aws ec2 authorize-security-group-ingress \
  --region us-east-1 --group-id "$SG_EC2" \
  --protocol tcp --port 80 --cidr 0.0.0.0/0

# HTTPS (443)
aws ec2 authorize-security-group-ingress \
  --region us-east-1 --group-id "$SG_EC2" \
  --protocol tcp --port 443 --cidr 0.0.0.0/0

# Spring Boot (8080)
aws ec2 authorize-security-group-ingress \
  --region us-east-1 --group-id "$SG_EC2" \
  --protocol tcp --port 8080 --cidr 0.0.0.0/0
```

#### 2.3. Criar Instância EC2

```bash
INSTANCE_ID=$(aws ec2 run-instances \
  --region us-east-1 \
  --image-id ami-0c55b159cbfafe1f0 \
  --instance-type t3.micro \
  --key-name "dsim-murakami-keypair" \
  --security-group-ids "$SG_EC2" \
  --tag-specifications \
    "ResourceType=instance,Tags=[{Key=Name,Value=dsim-murakami-server},{Key=Project,Value=dsim_murakami}]" \
  --query 'Instances[0].InstanceId' \
  --output text)

# Aguardar instância iniciar
aws ec2 wait instance-running \
  --region us-east-1 \
  --instance-ids "$INSTANCE_ID"

# Obter IP público
PUBLIC_IP=$(aws ec2 describe-instances \
  --region us-east-1 \
  --instance-ids "$INSTANCE_ID" \
  --query 'Reservations[0].Instances[0].PublicIpAddress' \
  --output text)

echo "EC2 IP Público: $PUBLIC_IP"
```

---

### Passo 3: Configurar EC2

#### 3.1. Conectar via SSH

```bash
ssh -i dsim-murakami-keypair.pem ec2-user@$PUBLIC_IP
```

#### 3.2. Instalar Dependências

```bash
# Atualizar sistema
sudo yum update -y

# Instalar Java 21
sudo yum install -y java-21-amazon-corretto-devel

# Instalar Maven
cd /tmp
wget https://dlcdn.apache.org/maven/maven-3/3.9.5/binaries/apache-maven-3.9.5-bin.tar.gz
sudo tar xzf apache-maven-3.9.5-bin.tar.gz -C /opt
sudo ln -s /opt/apache-maven-3.9.5 /opt/maven
sudo ln -s /opt/maven/bin/mvn /usr/local/bin/mvn

# Instalar NGINX
sudo amazon-linux-extras install nginx1 -y || sudo yum install nginx -y

# Instalar Git
sudo yum install -y git
```

#### 3.3. Configurar NGINX

```bash
sudo tee /etc/nginx/conf.d/dsim.conf > /dev/null <<'EOF'
server {
    listen 80;
    server_name _;

    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}

server {
    listen 443 ssl;
    server_name _;

    ssl_certificate /etc/nginx/ssl/dsim.crt;
    ssl_certificate_key /etc/nginx/ssl/dsim.key;

    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
EOF

# Criar certificado SSL autoassinado
sudo mkdir -p /etc/nginx/ssl
sudo openssl req -x509 -nodes -days 365 -newkey rsa:2048 \
    -keyout /etc/nginx/ssl/dsim.key \
    -out /etc/nginx/ssl/dsim.crt \
    -subj "/C=BR/ST=SP/L=SaoPaulo/O=DSIM/CN=dsim-murakami"

# Iniciar NGINX
sudo systemctl start nginx
sudo systemctl enable nginx
```

#### 3.4. Deploy do Backend

```bash
# Criar diretório
mkdir -p /home/ec2-user/dsim
cd /home/ec2-user/dsim

# Clonar repositório (ou fazer upload via SCP)
git clone https://github.com/FTakElu/DSIM_Murakami.git .

# Criar application-production.yml
cat > src/main/resources/application-production.yml <<EOF
server:
  port: 8080

spring:
  application:
    name: Sistema Monitoramento Pacientes - dsim_murakami
  
  datasource:
    url: jdbc:postgresql://$DB_ENDPOINT:5432/dsim_production
    username: dsim_admin
    password: SuaSenhaSegura123#
    driver-class-name: org.postgresql.Driver
  
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: false
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
EOF

# Compilar
mvn clean package -DskipTests

# Criar serviço systemd
sudo tee /etc/systemd/system/dsim.service > /dev/null <<EOF
[Unit]
Description=DSIM Murakami Spring Boot Application
After=network.target

[Service]
Type=simple
User=ec2-user
WorkingDirectory=/home/ec2-user/dsim
ExecStart=/usr/bin/java -jar /home/ec2-user/dsim/target/*.jar --spring.profiles.active=production
Restart=on-failure
RestartSec=10
StandardOutput=append:/home/ec2-user/dsim/application.log
StandardError=append:/home/ec2-user/dsim/application.log

[Install]
WantedBy=multi-user.target
EOF

# Iniciar serviço
sudo systemctl daemon-reload
sudo systemctl enable dsim
sudo systemctl start dsim

# Verificar status
sudo systemctl status dsim
```

---

### Passo 4: Configurar AWS Amplify

#### 4.1. Atualizar Configuração da API no Frontend

Edite `frontend-aws/js/api-config-cors.js`:

```javascript
// Substituir IP antigo pelo novo IP do EC2
const API_CONFIG = {
    BASE_URL: 'https://<SEU_IP_EC2>',
    // ... resto da configuração
};

// Na função apiRequest
window.apiRequest = async function(endpoint, options = {}) {
    const url = `https://<SEU_IP_EC2>${endpoint}`;
    // ...
};
```

#### 4.2. Commit e Push

```bash
git add frontend-aws/js/api-config-cors.js
git commit -m "Update: Novo endpoint AWS para dsim_murakami"
git push origin main
```

#### 4.3. Configurar Amplify via Console

1. Acesse: https://console.aws.amazon.com/amplify/
2. Clique em **"New app"** > **"Host web app"**
3. Selecione **GitHub** e autorize
4. Escolha o repositório: `FTakElu/DSIM_Murakami`
5. Escolha a branch: `main`
6. Configure build settings:

```yaml
version: 1
frontend:
  phases:
    build:
      commands:
        - echo "Frontend já está pronto, apenas fazendo deploy"
  artifacts:
    baseDirectory: frontend-aws
    files:
      - '**/*'
  cache:
    paths: []
```

7. Em **Advanced settings**:
   - App name: `dsim-murakami`
   - Environment: `production`

8. Clique em **"Save and deploy"**
9. Aguarde 3-5 minutos

---

## ⚙️ Configuração do Projeto

### Arquivos Atualizados

#### `src/main/resources/application-production.yml`
✅ Configurado automaticamente pelos scripts

#### `frontend-aws/js/api-config-cors.js`
⚠️ Requer atualização manual do IP do EC2

---

## 🔧 Troubleshooting

### Problema: RDS não aceita conexões

**Solução**: Verificar Security Group

```bash
aws ec2 describe-security-groups \
  --group-ids <SG_ID> \
  --query "SecurityGroups[0].IpPermissions"
```

### Problema: EC2 não responde

**Solução**: Verificar logs

```bash
ssh -i dsim-murakami-keypair.pem ec2-user@<PUBLIC_IP>
sudo systemctl status dsim
sudo journalctl -u dsim -f
```

### Problema: Frontend não conecta ao backend

**Solução**: 
1. Aceitar certificado SSL no navegador: `https://<PUBLIC_IP>/api/usuarios`
2. Verificar configuração em `api-config-cors.js`

### Problema: Erro ao criar/editar/excluir pacientes

**Correções aplicadas**:
- ✅ Corrigido uso de `localStorage` ao invés de `sessionStorage`
- ✅ Adicionado header `X-Usuario-Email` em todas as requisições
- ✅ Corrigida estrutura de dados enviada (informacaoMedica)
- ✅ Melhorado tratamento de erros com logs detalhados

---

## 📞 Comandos Úteis

### Ver logs do backend
```bash
ssh -i dsim-murakami-keypair.pem ec2-user@<PUBLIC_IP> \
  'tail -f /home/ec2-user/dsim/application.log'
```

### Reiniciar backend
```bash
ssh -i dsim-murakami-keypair.pem ec2-user@<PUBLIC_IP> \
  'sudo systemctl restart dsim'
```

### Ver status do banco
```bash
aws rds describe-db-instances \
  --db-instance-identifier dsim-murakami-db \
  --query 'DBInstances[0].[DBInstanceStatus,Endpoint.Address]'
```

### Parar recursos (para economizar)
```bash
# Parar EC2
aws ec2 stop-instances --instance-ids <INSTANCE_ID>

# Parar RDS
aws rds stop-db-instance --db-instance-identifier dsim-murakami-db
```

---

## ✅ Checklist de Verificação

- [ ] RDS criado e acessível
- [ ] EC2 criado e rodando
- [ ] NGINX configurado (portas 80 e 443)
- [ ] Backend Spring Boot inicializado
- [ ] Frontend no Amplify deployado
- [ ] API respondendo: `https://<EC2_IP>/api/usuarios`
- [ ] Frontend acessível: `https://dsim-murakami.amplifyapp.com`
- [ ] Login funcionando
- [ ] CRUD de pacientes funcionando
- [ ] Certificado SSL aceito no navegador

---

## 🎉 Pronto!

Sua infraestrutura AWS está configurada e o projeto **dsim_murakami** está online!

**URLs importantes**:
- Backend API: `https://<EC2_IP>/api`
- Frontend: `https://dsim-murakami.amplifyapp.com`
- Console RDS: https://console.aws.amazon.com/rds/
- Console EC2: https://console.aws.amazon.com/ec2/
- Console Amplify: https://console.aws.amazon.com/amplify/

**Suporte**: Para dúvidas, consulte a documentação AWS ou abra uma issue no GitHub.
