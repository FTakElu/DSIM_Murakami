
# 🏥 Sistema de Monitoramento de Pacientes — **DSIM**

> **Dispositivo de Segurança Inteligente para Monitoramento**  
> Sistema completo de **monitoramento e gerenciamento de pacientes** em ambiente hospitalar.

![License](https://img.shields.io/badge/license-MIT-blue.svg)
![Java](https://img.shields.io/badge/Java-21-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.5-brightgreen.svg)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?logo=html5&logoColor=white)
![Version](https://img.shields.io/badge/version-3.0.0-success.svg)
![AWS](https://img.shields.io/badge/AWS-Amplify%20%2B%20EC2-orange.svg)
![Deploy](https://img.shields.io/badge/deploy-Production-brightgreen.svg)

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
cd DSIM_Murakami

# 2. Execute o sistema
mvn spring-boot:run

# 3. Aguarde a mensagem de inicialização e acesse:
# 🌐 http://localhost:8080
```

### **🔑 Credenciais de Acesso**
- **Email**: `admin@sistema.com`
- **Senha**: `admin123`

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
- **🖥️ Backend API (EC2)**: http://54.237.230.21:8080
- **📊 Arquitetura**: Frontend HTTPS + Backend HTTP + Banco H2

### **🏗️ Arquitetura AWS**
```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   AWS AMPLIFY   │───▶│     AWS EC2     │───▶│    Banco H2     │
│   (Frontend)    │    │   (Backend)     │    │   (Em memória)  │
│     HTTPS       │    │  Spring Boot    │    │                 │
│  Static Hosting │    │   Java 21       │    │    Localhost    │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

### **⚙️ Tecnologias de Deploy**
- ✅ **Frontend**: AWS Amplify (deploy automático via GitHub)
- ✅ **Backend**: AWS EC2 t3.micro (Amazon Linux 2023)
- ✅ **CI/CD**: Integração GitHub → Amplify automática
- ✅ **SSL**: HTTPS no frontend via Amplify
- ✅ **Monitoramento**: CloudWatch logs habilitado

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

### 🚀 **FASE 3 - 04/11/2025 (CONCLUÍDA)**
- ✅ **Código Organizado**: CSS centralizado e componentes reutilizáveis
- ✅ **Navbar Universal**: Sistema de navegação unificado
- ✅ **Documentação**: README completo e instruções claras
- ✅ **Deploy AWS**: Frontend no Amplify + Backend no EC2
- ✅ **Produção**: Sistema funcionando em ambiente cloud
- ✅ **Integração**: Frontend HTTPS conectado ao backend HTTP via proxy CORS

---

## 📋 **Sobre o Projeto**

O **DSIM (Dispositivo de Segurança Inteligente para Monitoramento)** é uma aplicação web moderna que permite **monitorar sinais vitais de pacientes** de forma digital e acessível. O sistema foi projetado com **foco na experiência do usuário, segurança e eficiência**.

### ✨ **Características Principais**

* 🎨 **Interface Moderna e Responsiva** — Layout limpo, intuitivo e adaptável
* ⚙️ **Arquitetura MVC Completa** — Integração robusta entre frontend e backend
* 📊 **Monitoramento em Tempo Real** — Temperatura, batimentos cardíacos e oxigenação
* 👥 **Gestão Completa de Usuários** — CRUD completo com autenticação segura
* 🔔 **Sistema de Alertas Configurável** — Personalização por paciente e prioridade
* 💾 **Banco H2 em Memória** — Ideal para desenvolvimento e testes
* 🔐 **Segurança BCrypt** — Criptografia de senhas e validações rigorosas

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
- ✅ **Simulação em tempo real** de sinais vitais
- ✅ **Feedback visual dinâmico** baseado em status
- ✅ **Alertas automáticos** conforme configurações
- ✅ **Atualização sem refresh** da página

---

## 🛠️ **Tecnologias Utilizadas**

### ⚙️ **Backend**
| Tecnologia | Versão | Função |
|------------|--------|---------|
| ☕ **Java** | 21 | Linguagem principal |
| 🌱 **Spring Boot** | 3.1.5 | Framework web |
| 📊 **Spring Data JPA** | 3.1.5 | Persistência |
| 🌐 **Spring Web** | 3.1.5 | APIs REST |
| 🔐 **Spring Security** | BCrypt | Criptografia |
| 🗃️ **H2 Database** | Embutido | Banco em memória |
| 📦 **Lombok** | 1.18.30 | Redução de código |
| 🔧 **Maven** | 3.6+ | Build e dependências |

### 💻 **Frontend**
| Tecnologia | Versão | Função |
|------------|--------|---------|
| 🌐 **HTML5** | Padrão | Estrutura |
| 🎨 **CSS3** | Padrão | Estilização moderna |
| ⚡ **JavaScript** | ES2020+ | Interatividade |
| 📱 **Bootstrap** | 5.1.3 | Framework responsivo |
| 🎯 **Font Awesome** | 6.4.0 | Ícones |
| 📖 **Google Fonts** | Inter | Tipografia |

### ☁️ **Tecnologias AWS (Produção)**
| Tecnologia | Versão | Descrição |
|------------|--------|-----------|
| ☁️ **AWS Amplify** | - | Hospedagem frontend com CI/CD |
| 🖥️ **AWS EC2** | t3.micro | Servidor backend Linux |
| 🔐 **HTTPS/SSL** | TLS 1.3 | Certificado automático Amplify |
| 📊 **CloudWatch** | - | Logs e monitoramento |
| 🌐 **Proxy CORS** | - | Resolução Mixed Content |

---

## 📁 **Estrutura do Projeto**

```
DSIM_Murakami/
├── 📂 src/main/java/teste/
│   ├── 🎯 controller/          # Controladores REST
│   ├── 🏗️ model/              # Entidades JPA
│   ├── 📊 repository/          # Repositórios de dados
│   ├── ⚙️ service/            # Lógica de negócio
│   └── 🔧 config/             # Configurações
├── 📂 src/main/resources/
│   ├── 📄 application.yml     # Configurações da aplicação
│   └── 📊 data.sql           # Dados iniciais
├── 📂 src/main/webapp/view/   # Frontend local
│   ├── 🎨 css/               # Estilos centralizados
│   ├── ⚡ js/                # Scripts reutilizáveis
│   └── 📄 pages/             # Páginas HTML
├── 📂 frontend-aws/          # Frontend para produção (Amplify)
│   ├── 🎨 css/               # Estilos otimizados
│   ├── ⚡ js/                # Scripts com API config
│   ├── 📄 pages/             # Páginas HTML
│   ├── ⚙️ amplify.yml        # Config deploy Amplify
│   └── 🔄 _redirects         # Redirecionamentos SPA
│   └── 📱 pages/             # Páginas HTML
├── 📂 Diagramas/             # Documentação técnica
└── 📋 README.md              # Este arquivo
```
---

## 📊 **Acesso ao Banco (Opcional)**

Para inspecionar dados durante desenvolvimento:
- **URL**: http://localhost:8080/h2-console
- **JDBC URL**: `jdbc:h2:mem:dsimdb`
- **Usuário**: `dsim`
- **Senha**: (vazio)

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
- **Admin**: `admin@sistema.com` | `admin123`

### 🏥 **Pacientes**
- **Carlos Eduardo Silva** (85 anos, sinais estáveis)
- **Márcia dos Santos** (32 anos, atenção necessária)

### 🔔 **Alertas**
- Oxigenação baixa para Márcia
- Temperatura elevada para Márcia

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

### 🎯 **PROJETO COMPLETO E FUNCIONAL** ✅

- ✅ **Todas as funcionalidades** implementadas
- ✅ **Bugs corrigidos** e testados
- ✅ **Interface moderna** e responsiva
- ✅ **Código organizado** e documentado
- ✅ **Pronto para apresentação**

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
# 1. SSH na instância EC2
ssh -i "sua-chave.pem" ec2-user@IP-PUBLICO

# 2. Executar script de deploy
curl -O https://raw.githubusercontent.com/FTakElu/DSIM_Murakami/main/deploy-ec2.sh
chmod +x deploy-ec2.sh && ./deploy-ec2.sh

# 3. Verificar se está rodando
sudo systemctl status dsim
```

### **Configuração Completa**
1. **Frontend**: Alterar `API_BASE_URL` em `js/api-config-cors.js`
2. **Backend**: Configurar CORS para URL do Amplify
3. **Integração**: Testar comunicação frontend ↔ backend

---

## 📄 **Licença**

Este projeto é distribuído sob a licença [**MIT**](https://opensource.org/licenses/MIT).

---