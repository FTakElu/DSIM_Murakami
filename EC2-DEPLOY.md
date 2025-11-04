# 🚀 Deploy Backend DSIM no AWS EC2

## 📋 Pré-requisitos
- Conta AWS ativa
- EC2 Instance criada
- Chave SSH configurada
- Security Groups configurados

## 🖥️ Configurar Instância EC2

### 1. Criar EC2 Instance
```
- AMI: Amazon Linux 2023
- Type: t3.micro (free tier)
- Security Group: SSH (22), HTTP (80), Custom (8080)
- Key Pair: sua-chave.pem
```

### 2. Conectar via SSH
```bash
ssh -i "sua-chave.pem" ec2-user@SEU-IP-PUBLICO
```

### 3. Executar Script de Deploy
```bash
# No servidor EC2, execute:
curl -O https://raw.githubusercontent.com/FTakElu/DSIM_Murakami/main/deploy-ec2.sh
chmod +x deploy-ec2.sh
./deploy-ec2.sh
```

## 🔧 Comandos Úteis no Servidor

### Verificar Status
```bash
sudo systemctl status dsim
sudo journalctl -u dsim -f
```

### Controlar Serviço
```bash
sudo systemctl start dsim
sudo systemctl stop dsim  
sudo systemctl restart dsim
```

### Verificar Logs
```bash
tail -f /opt/dsim/app.log
```

## 🌐 Configurar Frontend

Após o backend estar rodando, atualize o frontend:

1. **Anote o IP público** da instância EC2
2. **Atualize o arquivo** `frontend-aws/js/api-config.js`:

```javascript
const API_CONFIG = {
    BASE_URL: 'http://SEU-IP-EC2:8080',
    // ... resto da config
};
```

3. **Commit e push** para atualizar o Amplify

## 🔒 Configurar CORS no Backend

No arquivo `WebConfig.java`, adicione:

```java
@CrossOrigin(origins = "https://main.d1234567890.amplifyapp.com")
```

## 📊 Monitoramento

### CloudWatch (Opcional)
- Configure logs automáticos
- Monitore CPU e memória
- Configure alertas

### Health Check
```bash
curl http://SEU-IP:8080/api/health
```

## 🔥 Troubleshooting

### Aplicação não inicia
```bash
# Verificar Java
java -version

# Verificar logs
sudo journalctl -u dsim -n 50

# Verificar porta
sudo netstat -tlnp | grep :8080
```

### Conexão recusada
- Verificar Security Groups (porta 8080 liberada)
- Verificar se aplicação está rodando
- Verificar firewall local

## 💰 Custos AWS (Free Tier)
- **EC2 t3.micro**: Grátis por 750 horas/mês (12 meses)
- **Data Transfer**: Primeiros 15GB grátis
- **EBS Storage**: 30GB grátis

## 🎯 Próximos Passos
1. ✅ Deploy backend no EC2
2. ✅ Atualizar configuração do frontend  
3. ✅ Migrar banco para RDS (opcional)
4. ✅ Configurar domínio personalizado (opcional)