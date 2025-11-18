# 🚀 INÍCIO RÁPIDO - DSIM Murakami AWS Setup

## ⚡ Setup Automatizado (Recomendado)

### Para Linux/Mac/Git Bash:

```bash
cd aws-setup

# Executar scripts em sequência
./01-setup-rds.sh       # Cria RDS PostgreSQL
./02-setup-ec2.sh       # Cria EC2 Instance
./03-deploy-backend.sh  # Deploy do Spring Boot
./04-setup-amplify.sh   # Instruções para Amplify
```

### Para Windows PowerShell:

```powershell
cd aws-setup
.\setup-aws-windows.ps1
```

---

## 📋 Pré-requisitos

1. **AWS CLI** instalado e configurado
   ```bash
   aws configure
   ```

2. **Git Bash** (Windows) ou terminal bash

3. **Conta AWS ativa**

---

## 🏗️ O que será criado

| Serviço | Tipo | Custo Mensal |
|---------|------|--------------|
| **RDS PostgreSQL** | db.t3.micro | ~$15 |
| **EC2** | t3.micro | Grátis (Free Tier) |
| **Amplify** | Frontend | Grátis |
| **Total** | | **~$15-25/mês** |

---

## 📖 Documentação Completa

Para setup manual detalhado, consulte:
- **[README-SETUP-AWS.md](./README-SETUP-AWS.md)** - Guia completo passo a passo

---

## ✅ Correções Aplicadas

### Problemas de CRUD Resolvidos:

1. ✅ **Autenticação corrigida**
   - Alterado `sessionStorage` para `localStorage`
   - Header `X-Usuario-Email` adicionado em todas as requisições

2. ✅ **Estrutura de dados ajustada**
   - `informacaoMedica` com campos corretos
   - Validações de campos opcionais

3. ✅ **Logs detalhados**
   - Console.log em todas as operações CRUD
   - Mensagens de erro mais claras

4. ✅ **Validações melhoradas**
   - Verificação de autenticação antes de operações
   - Tratamento de erros aprimorado

---

## 🎯 Estrutura dos Scripts

```
aws-setup/
├── 01-setup-rds.sh              # Cria RDS PostgreSQL
├── 02-setup-ec2.sh              # Cria EC2 + NGINX
├── 03-deploy-backend.sh         # Deploy Spring Boot
├── 04-setup-amplify.sh          # Instruções Amplify
├── setup-aws-windows.ps1        # Script PowerShell (Windows)
└── README-SETUP-AWS.md          # Documentação completa
```

---

## 🔍 Verificação Rápida

Após executar os scripts, verifique:

```bash
# Testar API
curl https://<EC2_IP>/api/usuarios

# Ver logs do backend
ssh -i dsim-murakami-keypair.pem ec2-user@<EC2_IP> \
  'tail -f /home/ec2-user/dsim/application.log'

# Status do RDS
aws rds describe-db-instances \
  --db-instance-identifier dsim-murakami-db \
  --query 'DBInstances[0].DBInstanceStatus'
```

---

## 🆘 Suporte

**Problemas?** Consulte a seção [Troubleshooting](./README-SETUP-AWS.md#troubleshooting) na documentação completa.

**Comandos úteis**:
```bash
# Parar EC2 (economizar)
aws ec2 stop-instances --instance-ids <INSTANCE_ID>

# Reiniciar backend
ssh -i dsim-murakami-keypair.pem ec2-user@<EC2_IP> 'sudo systemctl restart dsim'

# Ver status dos serviços
aws rds describe-db-instances --db-instance-identifier dsim-murakami-db
aws ec2 describe-instances --instance-ids <INSTANCE_ID>
```

---

## 🎉 Pronto!

Após a configuração, acesse:
- **Frontend**: `https://dsim-murakami.amplifyapp.com`
- **Backend API**: `https://<EC2_IP>/api`

---

**Projeto**: DSIM Murakami  
**Versão**: 2.0  
**Última atualização**: Novembro 2024
