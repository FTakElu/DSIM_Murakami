# 🐘 Migração para PostgreSQL - DSIM

## 📋 Visão Geral
Este projeto foi migrado do H2 Database para **PostgreSQL** com **dados limpos**. Todos os dados desnecessários foram removidos para garantir um ambiente de produção limpo.

## ⚠️ IMPORTANTE - LIMPEZA DE DADOS
- ✅ **Banco PostgreSQL limpo** - sem dados antigos
- ✅ **Apenas usuário admin essencial** - admin@dsim.com
- ✅ **Tabelas recriadas** com ddl-auto: create-drop
- ❌ **Dados de teste antigos removidos**
- ❌ **Pacientes de exemplo removidos**

## 🛠️ Configuração AWS RDS PostgreSQL

### 1. Criar Instância PostgreSQL na AWS
```bash
# Dar permissão de execução
chmod +x setup-postgresql-aws.sh

# Executar script (requer AWS CLI configurado)
./setup-postgresql-aws.sh
```

### 2. Configurar Security Group
No console AWS:
- Vá para RDS > Databases > sua-instancia
- Clique no Security Group
- Adicione regra: **Inbound > PostgreSQL (5432) > Source: EC2 Security Group**

## 🚀 Deploy no EC2

### 1. Compilar com Perfil PostgreSQL
```bash
# No seu computador local
mvn clean package -Pprod -DskipTests
```

### 2. Fazer Upload para EC2
```bash
# Substituir pela sua key e IP
scp -i sua-key.pem target/sistema-monitoramento-pacientes-1.0.0-SNAPSHOT.jar ec2-user@SEU-IP-EC2:/home/ec2-user/
scp -i sua-key.pem configure-ec2-postgresql.sh ec2-user@SEU-IP-EC2:/home/ec2-user/
```

### 3. Configurar EC2
```bash
# Na instância EC2
chmod +x configure-ec2-postgresql.sh

# IMPORTANTE: Editar o arquivo e colocar o endpoint correto do RDS
nano configure-ec2-postgresql.sh
# Alterar: DB_HOST="seu-endpoint-rds-real.amazonaws.com"

# Executar configuração
./configure-ec2-postgresql.sh
```

### 4. Iniciar Serviço
```bash
# Habilitar e iniciar
sudo systemctl enable dsim
sudo systemctl start dsim

# Verificar status
sudo systemctl status dsim

# Ver logs
sudo journalctl -u dsim -f
```

## 📊 Dados Iniciais Limpos

### Usuário Admin (ÚNICO)
```
Email: admin@dsim.com
Senha: admin123
```

### Estrutura do Banco
- ✅ **Tabela usuarios**: 1 admin
- ✅ **Tabela pacientes**: vazia (será populada pelo uso)
- ✅ **Tabela sinais_vitais**: vazia
- ✅ **Tabela alertas**: vazia
- ✅ **Tabela contato_emergencial**: vazia

## 🔧 Configurações de Produção

### application-prod.yml
```yaml
spring:
  datasource:
    url: jdbc:postgresql://${DB_HOST}:5432/${DB_NAME}
    username: ${DB_USER}
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: create-drop  # Recria tabelas limpas
```

### Variáveis de Ambiente EC2
```bash
export DB_HOST="seu-rds-endpoint.amazonaws.com"
export DB_NAME="dsim_clean"
export DB_USER="dsim_admin"
export DB_PASSWORD="DsimSecure2024!"
```

## 🧪 Teste da Migração

### 1. Verificar Banco
```bash
# Conectar ao PostgreSQL
PGPASSWORD=$DB_PASSWORD psql -h $DB_HOST -U $DB_USER -d $DB_NAME

# Verificar tabelas criadas
\dt

# Verificar usuário admin
SELECT * FROM usuario;

# Sair
\q
```

### 2. Testar APIs
```bash
# Teste de login
curl -X POST http://SEU-IP-EC2:8080/api/usuarios/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@dsim.com","senha":"admin123"}'

# Listar usuários
curl http://SEU-IP-EC2:8080/api/usuarios
```

## 📈 Benefícios da Migração

### ✅ Vantagens PostgreSQL
- **Banco gerenciado** pela AWS
- **Backups automáticos**
- **Alta disponibilidade**
- **Melhor performance**
- **Dados persistentes**

### 🧹 Limpeza Realizada
- **Remoção de pacientes de teste**
- **Remoção de dados desnecessários**
- **Estrutura limpa para produção**
- **Apenas dados essenciais**

## 🆘 Troubleshooting

### Erro de Conexão
```bash
# Verificar Security Group
# Verificar se RDS está "Available"
# Testar conectividade
telnet $DB_HOST 5432
```

### Erro de Aplicação
```bash
# Ver logs detalhados
sudo journalctl -u dsim -n 50

# Verificar variáveis de ambiente
sudo systemctl show dsim | grep Environment
```

### Resetar Banco (se necessário)
Como `ddl-auto: create-drop`, o banco é recriado a cada reinicialização:
```bash
sudo systemctl restart dsim
```

## 📞 Suporte
- **Logs**: `/var/log/dsim/application.log`
- **Serviço**: `sudo systemctl status dsim`
- **AWS RDS**: Console AWS > RDS > Monitoring