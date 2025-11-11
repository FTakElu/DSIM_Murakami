
# 🏥 Sistema de Monitoramento de Pacientes — **DSIM**

> **Dispositivo de Segurança Inteligente para Monitoramento**  
> Sistema completo de **monitoramento e gerenciamento de pacientes** em ambiente hospitalar com geração automática de sinais vitais.

![License](https://img.shields.io/badge/license-MIT-blue.svg)
![Java](https://img.shields.io/badge/Java-17-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.5-brightgreen.svg)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-13+-blue.svg)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?logo=html5&logoColor=white)
![Version](https://img.shields.io/badge/version-4.0.0-success.svg)
![AWS](https://img.shields.io/badge/AWS-Amplify%20%2B%20EC2%20%2B%20RDS-orange.svg)
![Deploy](https://img.shields.io/badge/deploy-Production%20Active-brightgreen.svg)

---

## 👥 **Integrantes do Grupo**

| Nome                                |
| ----------------------------------- | 
| **Arthur Barboza Mostaço**          |
| **Flávia Alessandra Elugo da Silva** | 
| **Sara Maria Falcão**               |
| **Stephany Caroline Carvalho**      | 

---

## 🚀 **EXECUÇÃO RÁPIDA (PROFESSOR)**

### **📋 Pré-requisitos**
- ☕ **Java 21+** instalado
- 🔧 **Maven 3.6+** instalado

### **⚡ Execução em 3 passos**
```bash
# 1. Navegue até a pasta do projeto
cd teste

# 2. Execute o sistema
mvn spring-boot:run

# 3. Aguarde a mensagem de inicialização e acesse:
# 🌐 http://localhost:8080
```

### **🔑 Credenciais de Acesso**
- **Email**: `admin@dsim.com`
- **Senha**: `admin123`

### **🔑 Credenciais PostgreSQL RDS (Produção)**
- **Host**: `dsim-postgres-20251109083108.cbx9vaugpv1l.us-east-1.rds.amazonaws.com`
- **Usuário**: `dsim_admin`
- **Senha**: `DSIM2025!Postgres`
- **Banco**: `dsim_postgres`

### **🛠️ Solução de Problemas**
Se der erro de porta ocupada:
```bash
# Windows - Mata processo na porta 8080
taskkill /F /PID <número_do_processo>

# Ou execute o script automático (se existir)
start-server.bat
```

---

## 🌐 **SISTEMA EM PRODUÇÃO (AWS)**

### **🚀 URLs de Acesso**
- **🌐 Frontend (Amplify)**: https://main.dd3d0c3znbvkh.amplifyapp.com
- **🖥️ Backend API (EC2)**: http://98.93.94.17:8080
- **📊 Arquitetura**: Frontend HTTPS + Backend HTTP + PostgreSQL RDS

### **✅ STATUS**: **SISTEMA OPERACIONAL EM PRODUÇÃO**
- ✅ **Backend**: Spring Boot ativo no EC2 IP 98.93.94.17:8080
- ✅ **Banco**: PostgreSQL RDS conectado e operacional
- ✅ **Geração Automática**: Sinais vitais sendo gerados a cada minuto
- ✅ **APIs**: Todas funcionando (usuários, pacientes, login, sinais vitais)
- ✅ **CORS**: Configurado para integração frontend-backend
- ✅ **Logs**: CloudWatch ativo com monitoramento completo

### **🏗️ Arquitetura AWS**
```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────────┐
│   AWS AMPLIFY   │───▶│     AWS EC2     │───▶│   PostgreSQL RDS    │
│   (Frontend)    │    │   (Backend)     │    │   (Produção)        │
│     HTTPS       │    │  IP: 98.93.94.17│    │  Auto Sinais Vitais │
│  Static Hosting │    │   Java 17       │    │  dsim_postgres      │
└─────────────────┘    └─────────────────┘    └─────────────────────┘
          │                       │                       │
          │                       │                       │
      📱 Frontend              🖥️ Backend             🗄️ Database
   Bootstrap + CSS         Spring Boot 3.1.5      PostgreSQL 13+
   Responsive Design       Maven 3.8.8            HikariCP Pool
```

### **⚙️ Recursos em Produção**
- ✅ **Sistema de Geração Automática**: Sinais vitais criados automaticamente
- ✅ **Backend Robusto**: Spring Boot com conexão HikariCP ao PostgreSQL
- ✅ **Frontend Responsivo**: AWS Amplify com HTTPS automático
- ✅ **CORS Configurado**: Comunicação segura entre domínios
- ✅ **Logs CloudWatch**: Monitoramento completo da aplicação
- ✅ **Auto-Deploy**: GitHub → Amplify integração automática

---

## �📚 **Contexto Acadêmico**

Este projeto foi desenvolvido para a disciplina **Linguagem de Programação 2 (LP2)**, com foco em:
- **Arquitetura MVC** completa
- **Integração Frontend-Backend**
- **Boas práticas de desenvolvimento**
- **Interface responsiva e moderna**

---

## 📅 **Cronograma de Desenvolvimento**

### ✅ **FASE 1 - 28/10/2025 (CONCLUÍDA)**
- ✅ **Caso de Uso**: Cadastrar Paciente (`/pages/adicionar-paciente.html`)
- ✅ **Caso de Uso**: Dashboard de Pacientes (`/pages/pacientes.html`) 
- ✅ **Repositório**: Criado no GitHub
- ✅ **Diagramas**: Caso de uso, classe e arquitetura

### ✅ **FASE 2 - 04/11/2025 (CONCLUÍDA)**
- ✅ **Caso de Uso**: Gerenciar Usuários
- ✅ **Caso de Uso**: Detalhes de Paciente
- ✅ **Frontend**: Interface aprimorada e responsiva
- ✅ **Sistema de Alertas**: Configuração personalizada

### 🚀 **FASE 3 - 11/11/2025 (CONCLUÍDA)**
- ✅ **Código Organizado**: CSS centralizado e componentes reutilizáveis
- ✅ **Navbar Universal**: Sistema de navegação unificado
- ✅ **Deploy AWS**: Frontend no Amplify + Backend no EC2 + RDS PostgreSQL
- ✅ **Produção Completa**: Sistema 100% operacional em AWS
- ✅ **Integração Full-Stack**: Frontend HTTPS ↔ Backend HTTP ↔ PostgreSQL RDS
- ✅ **Geração Automática**: Sistema de sinais vitais automático implementado
- ✅ **Documentação**: README atualizado com configurações reais de produção

---

## 📋 **Sobre o Projeto**

O **DSIM (Dispositivo de Segurança Inteligente para Monitoramento)** é uma aplicação web moderna que permite **monitorar sinais vitais de pacientes** de forma digital e acessível. O sistema foi projetado com **foco na experiência do usuário, segurança e eficiência**.

### ✨ **Características Principais**

* 🎨 **Interface Moderna e Responsiva** — Layout limpo, intuitivo e adaptável a qualquer dispositivo
* ⚙️ **Arquitetura Full-Stack AWS** — Integração completa Frontend (Amplify) + Backend (EC2) + Database (RDS)
* 📊 **Geração Automática de Sinais Vitais** — Sistema inteligente que gera dados realistas a cada minuto
* � **Monitoramento em Tempo Real** — Temperatura, batimentos cardíacos e oxigenação atualizados automaticamente
* 👥 **Gestão Completa de Usuários** — CRUD completo com autenticação segura e criptografia BCrypt
* 🔔 **Sistema de Alertas Configurável** — Personalização por paciente e prioridade com notificações automáticas
* �️ **PostgreSQL RDS em Produção** — Banco de dados robusto na nuvem AWS com alta disponibilidade
* 🔐 **Segurança Empresarial** — CORS configurado, HTTPS no frontend e validações rigorosas

---

## 🚀 **Funcionalidades Implementadas**

### 🏠 **Dashboard Principal**
- ✅ **Painel interativo** com estatísticas em tempo real
- ✅ **Cards de pacientes** com sinais vitais atualizados
- ✅ **Indicadores visuais** (normal, atenção, crítico)
- ✅ **Navegação intuitiva** entre seções

### 🔐 **Sistema de Autenticação**
- ✅ **Login seguro** com validação de credenciais
- ✅ **Cadastro simplificado** de usuários
- ✅ **Criptografia BCrypt** para senhas
- ✅ **Validação de email único**

### 👥 **Gestão de Usuários**
- ✅ **CRUD completo**: criar, visualizar, editar, excluir
- ✅ **Busca e filtros** por nome e status
- ✅ **Ativação/Desativação** de contas
- ✅ **Interface responsiva** com modais

### 🏥 **Gestão de Pacientes**
- ✅ **Cadastro completo** (dados pessoais, médicos, emergência)
- ✅ **Campo deficiência** nas informações médicas
- ✅ **Edição preservando dados** (correção de bug de sinais vitais zerados)
- ✅ **Exclusão com dupla confirmação** (correção de bug de exclusão)

### 🔔 **Sistema de Alertas**
- ✅ **Configuração por paciente** com limites personalizados
- ✅ **Múltiplos tipos**: oxigenação, temperatura, batimentos
- ✅ **Níveis de prioridade**: baixa, média, alta, crítica
- ✅ **Interface completa** para criação, edição e exclusão
- ✅ **Validações robustas** de valores

### 📊 **Monitoramento Avançado**
- ✅ **Geração Automática de Dados** — Sistema de sinais vitais que gera valores realistas automaticamente
- ✅ **Atualização em Tempo Real** — Interface se atualiza automaticamente sem necessidade de refresh
- ✅ **Feedback Visual Dinâmico** baseado em status dos pacientes (normal, atenção, crítico)
- ✅ **Alertas Automáticos** conforme configurações personalizadas por paciente
- ✅ **Persistência PostgreSQL** — Todos os dados são salvos permanentemente na nuvem AWS RDS

---

## 🛠️ **Tecnologias Utilizadas**

### ⚙️ **Backend**
| Tecnologia | Versão | Função |
|------------|--------|---------|
| ☕ **Java** | 17 | Linguagem principal |
| 🌱 **Spring Boot** | 3.1.5 | Framework web |
| 📊 **Spring Data JPA** | 3.1.5 | Persistência |
| 🌐 **Spring Web** | 3.1.5 | APIs REST |
| 🔐 **Spring Security** | BCrypt | Criptografia |
| 🐘 **PostgreSQL** | 13+ | Banco de dados produção (AWS RDS) |
| 🗃️ **H2 Database** | Embutido | Banco desenvolvimento (local) |
| 📦 **Lombok** | 1.18.30 | Redução de código |
| 🔧 **Maven** | 3.8.8 | Build e dependências |
| ⚡ **HikariCP** | - | Pool de conexões PostgreSQL |

### 💻 **Frontend**
| Tecnologia | Versão | Função |
|------------|--------|---------|
| 🌐 **HTML5** | Padrão | Estrutura |
| 🎨 **CSS3** | Padrão | Estilização moderna |
| ⚡ **JavaScript** | ES2020+ | Interatividade |
| 📱 **Bootstrap** | 5.3.0 | Framework responsivo |
| 🎯 **Font Awesome** | 6.4.0 | Ícones |
| 📖 **Google Fonts** | Inter | Tipografia |

### ☁️ **Tecnologias AWS (Produção)**
| Tecnologia | Versão | Descrição |
|------------|--------|-----------|
| ☁️ **AWS Amplify** | - | Hospedagem frontend com CI/CD automático via GitHub |
| 🖥️ **AWS EC2** | t3.micro | Servidor backend Amazon Linux 2023 (IP: 98.93.94.17) |
| 🐘 **AWS RDS PostgreSQL** | 13+ | Banco de dados gerenciado (dsim-postgres-20251109083108...) |
| 🔐 **HTTPS/SSL** | TLS 1.3 | Certificado automático AWS (frontend) |
| 📊 **CloudWatch** | - | Logs e monitoramento completo da aplicação |
| 🌐 **CORS** | - | Comunicação segura HTTPS frontend → HTTP backend |
| 🔄 **Auto-Deploy** | - | GitHub push → Amplify deploy automático |

---

## 📁 **Estrutura do Projeto**

```
teste/
├── 📂 src/main/java/teste/
│   ├── 🎯 controller/          # Controladores REST
│   ├── 🏗️ model/              # Entidades JPA
│   ├── 📊 repository/          # Repositórios de dados
│   ├── ⚙️ service/            # Lógica de negócio
│   └── 🔧 config/             # Configurações (CORS, segurança)
├── 📂 src/main/resources/
│   ├── 📄 application.yml     # Configurações locais (H2)
│   ├── 📄 application-prod.yml # Configurações produção (PostgreSQL)
│   └── 📊 data.sql           # Dados iniciais (usuário admin)
├── 📂 src/main/webapp/view/   # Frontend local
│   ├── 🎨 css/               # Estilos centralizados
│   ├── ⚡ js/                # Scripts reutilizáveis
│   └── 📄 pages/             # Páginas HTML
├── 📂 Diagramas/             # Documentação técnica UML
├── 📋 pom.xml               # Dependências Maven
└── 📋 README.md             # Este arquivo (atualizado)
```
---

## 📊 **Acesso ao Banco**

### **🏠 Desenvolvimento (Local)**
Para inspecionar dados durante desenvolvimento:
- **URL**: http://localhost:8080/h2-console
- **JDBC URL**: `jdbc:h2:mem:dsimdb`
- **Usuário**: `dsim`
- **Senha**: (vazio)

### **☁️ Produção (AWS RDS PostgreSQL)**
Banco de dados em produção (apenas para referência):
- **Host**: `dsim-postgres-20251109083108.cbx9vaugpv1l.us-east-1.rds.amazonaws.com`
- **Porta**: `5432`
- **Banco**: `dsim_postgres`
- **Usuário**: `dsim_admin`
- **Senha**: `DSIM2025!Postgres`

---

## 🔧 **Comandos Úteis**

```bash
# 🚀 Executar aplicação
mvn spring-boot:run

# 🧹 Limpar e compilar
mvn clean compile  

# 📦 Gerar JAR
mvn package -DskipTests

# ⚡ Compilação rápida
mvn compile

# 🔍 Debug completo
mvn spring-boot:run -X

# 💀 Matar processo (Windows)
taskkill /F /IM java.exe
```

---

## 🧪 **Dados de Teste**

O sistema inicializa automaticamente com:

### 👤 **Usuários**
- **Admin**: `admin@dsim.com` | `admin123`

### 🏥 **Pacientes**
- **João Silva** (Masculino, sinais estáveis) - Dados criados automaticamente pelo sistema
- **Sistema Automático** gera sinais vitais realistas para todos os pacientes a cada minuto

### **📊 Monitoramento Atual**
- ✅ **Temperatura**: 36.5°C - 37.5°C (normal)
- ✅ **Batimentos**: 70-100 bpm (normal)  
- ✅ **Oxigenação**: 95%-100% (normal)
- 🔄 **Atualização**: Automática a cada 60 segundos

---

## 📱 **URLs Principais**

| Funcionalidade | URL |
|----------------|-----|
| 🏠 **Início** | `http://localhost:8080/` |
| 📊 **Dashboard** | `http://localhost:8080/pages/pacientes.html` |
| 👥 **Usuários** | `http://localhost:8080/pages/usuarios.html` |
| ➕ **Novo Paciente** | `http://localhost:8080/pages/adicionar-paciente.html` |
| 🔔 **Alertas** | `http://localhost:8080/pages/configurar-alertas.html` |
| 🔐 **Login** | `http://localhost:8080/pages/login.html` |

---

## 📝 **Status do Projeto**

### 🎯 **PROJETO COMPLETO E OPERACIONAL EM PRODUÇÃO** ✅

- ✅ **Sistema Completamente Funcional** em AWS (Amplify + EC2 + RDS)
- ✅ **Geração Automática de Dados** — Sinais vitais gerados automaticamente a cada minuto
- ✅ **Backend Robusto** — Spring Boot conectado ao PostgreSQL RDS com HikariCP
- ✅ **Frontend Responsivo** — AWS Amplify com HTTPS e deploy automático via GitHub
- ✅ **Banco de Dados Persistente** — PostgreSQL RDS 13+ com dados permanentes
- ✅ **Monitoramento CloudWatch** — Logs e métricas em tempo real
- ✅ **CORS Configurado** — Comunicação segura entre frontend HTTPS e backend HTTP
- ✅ **Documentação Atualizada** — README com todas as configurações reais de produção

---

## 🚀 **Como Fazer Deploy**

### **Frontend (AWS Amplify)**
```bash
# 1. O deploy é automático via GitHub
# 2. Cada push na branch main dispara novo deploy
# 3. Amplify detecta automaticamente o amplify.yml
# 4. Build e deploy em ~3 minutos
```

### **Backend (AWS EC2)**
```bash
# 1. SSH na instância EC2 com a nova chave
ssh -i "dsim-keypair-us-east-1.pem" ec2-user@98.93.94.17

# 2. Verificar se aplicação está rodando
sudo ps aux | grep java

# 3. Ver logs em tempo real
tail -f nohup.out

# 4. Restart se necessário
pkill java && nohup mvn spring-boot:run > nohup.out 2>&1 &
```

### **Configuração Completa**
1. **Frontend**: Alterar `API_BASE_URL` em `js/api-config-cors.js` para `http://98.93.94.17:8080`
2. **Backend**: CORS já configurado para URL do Amplify
3. **PostgreSQL**: RDS conectado automaticamente
4. **Integração**: Sistema testado e funcionando frontend ↔ backend ↔ database

### **🔍 Verificação do Sistema**
```bash
# Testar API backend diretamente
curl http://98.93.94.17:8080/api/usuarios

# Verificar conexão PostgreSQL
curl http://98.93.94.17:8080/api/pacientes

# Status da aplicação Spring Boot
curl http://98.93.94.17:8080/actuator/health
```

---

## 📄 **Licença**

Este projeto é distribuído sob a licença [**MIT**](https://opensource.org/licenses/MIT).

---
