# 🏥 Sistema de Monitoramento de Pacientes — DSIM Murakami

> Dispositivo de Segurança Inteligente para Monitoramento
> Sistema completo de monitoramento e gerenciamento de pacientes em ambiente hospitalar com geração automática de sinais vitais.

---

## 📦 Estrutura do Projeto
- `aws-setup/`: Scripts e instruções para deploy AWS (EC2, RDS, Amplify)
- `frontend-aws/`: Frontend para AWS Amplify (páginas HTML, CSS, JS)
- `src/`: Backend Spring Boot (controllers, services, models, repositories)
- `diagrams/`: Diagramas de arquitetura e entidades UML
- `scripts/`: Scripts de deploy e configuração (deploy-nova-sessao.bat, setup-dsim.bat)
- `config/`: Arquivos de configuração (amplify.yml, pom.xml)

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
- [`frontend-aws/`](frontend-aws/README.md): Frontend para Amplify (HTML/CSS/JS)
- [`src/`](src/README.md): Backend Spring Boot (API REST)
- [`diagrams/`](diagrams/README.md): Diagramas UML do projeto

## 🏗️ Arquitetura

### Backend (Spring Boot + JPA)
- **Controllers**: Endpoints REST com padrão `ManterXController` (ex: ManterPacienteController)
- **Services**: Lógica de negócio com padrão `ManterXService` para CRUD (ex: ManterPacienteService)
- **Repositories**: Acesso a dados seguindo padrão Spring Data JPA: `XRepository` (ex: PacienteRepository)
- **Models**: Entidades JPA (Paciente, Usuario, Alerta, SinaisVitais, etc)
- **Enums**: Enumerações em pacote separado (TipoAlerta, NivelPrioridade, StatusSinal)
- **Schedulers**: Serviços agendados (SinaisVitaisAutomaticoService - executa a cada 5 minutos)

### Frontend (HTML/CSS/JS)
Páginas seguem nomenclatura dos casos de uso:
- `cadastrar-usuario.html`: UC01 - Cadastrar Usuário
- `login.html`: UC02 - Autenticar Usuário
- `cadastrar-paciente.html`: UC03 - Cadastrar Paciente
- `configurar-alertas.html`: UC04 - Configurar Alertas
- `visualizar-painel-pacientes.html`: UC05 - Visualizar Painel de Pacientes
- `visualizar-informacoes-paciente.html`: UC06 - Visualizar Informações do Paciente
- `visualizar-painel-usuarios.html`: Gerenciar Usuários (Admin)

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
