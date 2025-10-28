# 🏥 Sistema de Monitoramento de Pacientes DSIM

> **Sistema completo de monitoramento e gerenciamento de pacientes para ambiente hospitalar**

## 📚 Contexto Acadêmico

Como havíamos alinhado com o professor, estamos inscritos na disciplina **LP2** (Linguagem de Programação 2). Neste caso, foi pedido para que deixássemos apenas nosso diagrama no repositório e nosso código, e fizéssemos um cronograma para que fôssemos acompanhando o desenvolvimento.

## 📅 Cronograma de Desenvolvimento

### ✅ **28/10/2025 - ENTREGUE**
- ✅ **Caso de Uso**: Cadastrar Paciente 
localhost:8080/pages/adicionar-paciente.html
- ✅ **Caso de Uso**: Visualizar Painel do Paciente localhost:8080/pages/pacientes.html 
- ✅ **Repositório**: Criar repositório no GitHub
- ✅ **Diagramas**: Correção dos diagramas de classe, arquitetura e caso de uso

### 🔄 **04/11/2025 - EM DESENVOLVIMENTO**
- 🔄 **Caso de Uso**: Cadastrar Usuário
- 🔄 **Caso de Uso**: Detalhes de Paciente
- 🔄 **Frontend**: Ajustar e melhorar interface

### ⏳ **11/11/2025 - PLANEJADO**
- ⏳ **Caso de Uso**: Configurar Alarmes
- ⏳ **Deploy**: Subir projeto no Amplify da AWS
- ⏳ **Qualidade**: Implementar testes automatizados

![License](https://img.shields.io/badge/license-MIT-blue.svg)
![Java](https://img.shields.io/badge/Java-21-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.5-brightgreen.svg)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?logo=html5&logoColor=white)

## 📋 Sobre o Projeto

O **DSIM (Dispositivo de Segurança Inteligente para Monitoramento)** é um sistema moderno e completo para monitoramento de pacientes em ambientes hospitalares. Desenvolvido com foco na usabilidade, performance e compatibilidade, oferece interfaces intuitivas para cadastro, monitoramento e gestão de pacientes.

### ✨ Principais Características
- 🎨 **Interface Moderna**: Design responsivo e intuitivo
- 🔧 **Tecnologia Pura**: HTML/CSS/JavaScript para máxima compatibilidade
- 📊 **Monitoramento Real**: Simulação de sinais vitais em tempo real
- 👥 **Multi-usuário**: Sistema de autenticação e perfis de usuário
- 📱 **Responsivo**: Funciona perfeitamente em desktop e mobile

## 🚀 Funcionalidades Implementadas

### 🏠 **Página Inicial**
- Apresentação do sistema DSIM
- Design moderno com gradientes e animações
- Navegação intuitiva para login e cadastro

### 🔐 **Sistema de Autenticação**
- Login com validação de credenciais
- Cadastro de novos usuários
- Diferentes perfis: Familiar, Cuidador, Médico, Administrador
- Usuários de teste pré-configurados

### 👥 **Gestão de Pacientes**
- **Dashboard de Pacientes**: Visão geral com estatísticas
- **Cadastro Completo**: Informações pessoais, médicas e de emergência
- **Monitoramento**: Sinais vitais em tempo real
- **Cards Informativos**: Visualização organizada dos dados

### 📊 **Monitoramento Avançado**
- Simulação de sinais vitais (oxigenação, temperatura, batimentos)
- Indicadores visuais de status (normal, atenção, crítico)
- Atualização em tempo real dos dados
- Interface intuitiva para acompanhamento

## 🏗️ Arquitetura do Sistema

### **Padrão MVC Implementado Corretamente**

O projeto segue rigorosamente o padrão **Model-View-Controller (MVC)**:

```
#### **📂 MODEL** - `src/main/java/teste/model/`
```
├── 📁 model/                    # Entidades de Dados (JPA)
│   ├── Paciente.java           # Entidade principal do paciente
│   ├── Usuario.java            # Usuários do sistema
│   ├── ContatoEmergencial.java # Contatos de emergência
│   ├── InformacaoMedica.java   # Dados médicos
│   └── SinaisVitais.java       # Monitoramento vital
```

#### **🎮 CONTROLLER** - `src/main/java/teste/controller/`
```
├── 📁 controller/              # Controladores REST (Lógica de Controle)
│   ├── PacienteController.java # API de pacientes
│   ├── UsuarioController.java  # API de usuários
│   ├── DashboardController.java# API do dashboard
│   └── HomeController.java     # Controlador principal
```

#### **🖥️ VIEW** - `src/main/webapp/view/`
```
├── 📁 view/                    # Interface do Usuário (Apresentação)
│   ├── index.html             # Página inicial
│   ├── 📁 pages/              # Páginas da aplicação
│   │   ├── login.html         # Tela de login
│   │   ├── cadastro.html      # Cadastro de usuários
│   │   ├── pacientes.html     # Dashboard de pacientes
│   │   └── adicionar-paciente.html # Cadastro de paciente
│   ├── 📁 assets/             # Recursos estáticos
│   └── 📁 css/               # Estilos CSS
```

#### **⚙️ CAMADAS DE APOIO**
```
├── 📁 service/                # Lógica de Negócio
│   ├── ManterPacienteService.java
│   └── UsuarioService.java
│
├── 📁 repository/             # Acesso a Dados (Spring Data JPA)  
│   ├── PacienteRepository.java
│   └── UsuarioRepository.java
│
└── 📁 config/                 # Configurações
    └── WebConfig.java         # Configuração MVC
```

#### **🎮 CONTROLLER** - `src/main/java/teste/controller/`
```java
├── 📁 controller/     # Controladores REST (Lógica de Controle)
│   ├── PacienteController.java  # API de pacientes
│   ├── UsuarioController.java   # API de usuários
│   ├── DashboardController.java # API do dashboard
│   └── HomeController.java      # Controlador principal
```

#### **🖥️ VIEW** - `src/main/webapp/view/`
```html
├── 📁 view/           # Interface do Usuário (Apresentação)
│   ├── index.html           # Página inicial
│   ├── 📁 pages/           # Páginas da aplicação
│   │   ├── login.html      # Tela de login
│   │   ├── cadastro.html   # Cadastro de usuários
│   │   ├── pacientes.html  # Dashboard de pacientes
│   │   └── adicionar-paciente.html # Cadastro de paciente
│   ├── 📁 assets/          # Recursos estáticos
│   └── 📁 css/            # Estilos CSS
```

#### **⚙️ CAMADAS DE APOIO**
```java
├── 📁 service/        # Lógica de Negócio
│   ├── ManterPacienteService.java
│   └── UsuarioService.java
│
├── 📁 repository/     # Acesso a Dados (Spring Data JPA)  
│   ├── PacienteRepository.java
│   └── UsuarioRepository.java
│
└── 📁 config/         # Configurações
    └── WebConfig.java  # Configuração MVC
```
```

## 🛠️ Tecnologias Utilizadas

### **Backend**
- ☕ **Java 21** - Linguagem principal
- 🌱 **Spring Boot 3.1.5** - Framework principal  
- 📊 **Spring Data JPA** - Persistência de dados
- 🌐 **Spring Web** - APIs REST
- 🗃️ **H2 Database** - Banco em memória para desenvolvimento
- 🔧 **Maven** - Gerenciamento de dependências

### **Frontend** 
- 🌐 **HTML5** - Estrutura das páginas
- 🎨 **CSS3** - Estilização moderna com Flexbox e Grid
- ⚡ **JavaScript ES6+** - Interatividade e validações
- 📱 **Design Responsivo** - Compatível com todos os dispositivos
- 🎯 **Font Awesome** - Ícones modernos
- 🔤 **Google Fonts** - Tipografia (Inter)

## � Status Detalhado das Entregas

