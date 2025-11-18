# 🏥 Sistema de Monitoramento de Pacientes — DSIM Murakami

> Dispositivo de Segurança Inteligente para Monitoramento
> Sistema completo de monitoramento e gerenciamento de pacientes em ambiente hospitalar com geração automática de sinais vitais.

---

## 📦 Estrutura do Projeto
- `aws-setup/README.md`: Scripts e instruções para deploy AWS (EC2, RDS, Amplify)
- `frontend-aws/README.md`: Frontend para AWS Amplify
- `src/README.md`: Backend Spring Boot
- `Diagramas/README.md`: Diagramas de arquitetura e entidades

---

## 🚀 Como Implantar o Projeto (Passo a Passo)

### 1. **Pré-requisitos**
- Java 21+
- Maven 3.6+
- Conta AWS (EC2, RDS, Amplify)
- Git

### 2. **Clonar o Repositório**
```bash
# Clone o projeto
https://github.com/FTakElu/DSIM_Murakami.git
cd DSIM_Murakami
```

### 3. **Configurar AWS**
- Siga o passo a passo em `aws-setup/README.md` para:
  - Criar banco RDS PostgreSQL
  - Criar instância EC2
  - Configurar backend
  - Deploy do frontend no Amplify

### 4. **Deploy Backend**
- Compile e envie o backend para EC2:
```bash
cd aws-setup
./03-deploy-backend.sh
```
- O backend será acessível em:
  - `http://<IP-EC2>:8080/api`

### 5. **Deploy Frontend**
- Configure o Amplify conforme instruções em `frontend-aws/README.md`
- O frontend será acessível em:
  - `https://main.<dominio>.amplifyapp.com`

### 6. **Acessar o Sistema**
- Frontend: `https://main.d2jxbir2dzq8xg.amplifyapp.com`
- Backend: `http://3.237.26.213:8080/api`
- Banco RDS: `dsim-postgres-20251109083108.cbx9vaugpv1l.us-east-1.rds.amazonaws.com`

### 7. **Credenciais Padrão**
- Email: `admin@dsim.com`
- Senha: `admin123`

---

## 🛠️ Solução de Problemas
- Verifique logs do backend via SSH na EC2
- Aceite certificado HTTPS self-signed se necessário
- Consulte os READMEs dos subdiretórios para detalhes específicos

---

## 📁 Subdiretórios
- [`aws-setup/`](aws-setup/README.md): Scripts de automação AWS
- [`frontend-aws/`](frontend-aws/README.md): Frontend para Amplify
- [`src/`](src/README.md): Backend Spring Boot
- [`Diagramas/`](Diagramas/README.md): Diagramas do projeto

---

## 👥 Integrantes
| Nome                                |
| ----------------------------------- |
| Arthur Barboza Mostaço              |
| Flávia Alessandra Elugo da Silva    |
| Sara Maria Falcão                   |
| Stephany Caroline Carvalho          |

---

## 📜 Licença
MIT
