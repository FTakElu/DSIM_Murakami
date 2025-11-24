# Backend (src) - DSIM Murakami# Backend (src) - DSIM Murakami



Este diretório contém o código fonte do backend Spring Boot.Este diretório contém o código fonte do backend Spring Boot.



## 📁 Estrutura- `main/java`: Código Java (controllers, services, models)

- `main/resources`: Configurações, templates, arquivos estáticos

```- `main/webapp`: Views e assets

src/

├── main/O backend conecta-se ao banco PostgreSQL RDS e expõe a API REST para o frontend.

│   ├── java/teste/

│   │   ├── SistemaMonitoramentoPacientesApplication.java  # Main classConsulte o README da raiz para instruções de deploy e configuração.
│   │   ├── config/                                        # Configurações
│   │   │   ├── SecurityConfig.java                        # CORS e Segurança
│   │   │   ├── WebConfig.java                             # Configuração Web
│   │   │   └── DadosIniciais.java                         # Dados iniciais (seed)
│   │   ├── controller/                                    # REST Controllers
│   │   │   ├── ManterPacienteController.java              # CRUD Pacientes
│   │   │   ├── ManterUsuarioController.java               # CRUD Usuários
│   │   │   ├── ManterAlertaController.java                # CRUD Alertas
│   │   │   └── ManterConfiguracaoAlertaController.java    # Config Alertas
│   │   ├── service/                                       # Lógica de Negócio
│   │   │   ├── ManterPacienteService.java                 # Service Pacientes
│   │   │   ├── ManterUsuarioService.java                  # Service Usuários
│   │   │   ├── ManterAlertaService.java                   # Service Alertas
│   │   │   ├── ManterConfiguracaoAlertaService.java       # Service Config
│   │   │   ├── SinaisVitaisService.java                   # Geração sinais vitais
│   │   │   └── SinaisVitaisAutomaticoService.java         # @Scheduled (5 min)
│   │   ├── repository/                                    # Spring Data JPA
│   │   │   ├── PacienteRepository.java
│   │   │   ├── UsuarioRepository.java
│   │   │   ├── AlertaRepository.java
│   │   │   ├── ConfiguracaoAlertaRepository.java
│   │   │   └── SinaisVitaisRepository.java
│   │   └── model/                                         # Entidades JPA
│   │       ├── Paciente.java
│   │       ├── Usuario.java
│   │       ├── Alerta.java
│   │       ├── ConfiguracaoAlerta.java
│   │       ├── SinaisVitais.java
│   │       ├── InformacaoMedica.java
│   │       ├── ContatoEmergencial.java
│   │       └── enums/                                     # Enumerações
│   │           ├── TipoAlerta.java
│   │           ├── NivelPrioridade.java
│   │           └── StatusSinal.java
│   └── resources/
│       ├── application.yml                                # Config principal
│       ├── application-prod.yml                           # Config produção
│       ├── application-production.yml                     # Config AWS
│       └── static/
└── test/
    └── java/                                              # Testes unitários
```

## 🎯 Padrões de Nomenclatura

### Controllers (REST API)
- **Padrão**: `ManterXController`
- **Exemplo**: ManterPacienteController, ManterUsuarioController
- **Propósito**: Endpoints REST mapeando casos de uso

### Services (Lógica de Negócio)
- **CRUD**: `ManterXService` (ex: ManterPacienteService)
- **Especializados**: Sem "Manter" (ex: SinaisVitaisService, SinaisVitaisAutomaticoService)

### Repositories (Acesso a Dados)
- **Padrão Spring Data JPA**: `XRepository`
- **Exemplo**: PacienteRepository, UsuarioRepository
- **Nota**: NÃO usam "Manter" (convenção Spring)

## 🔧 Tecnologias

- Java 21
- Spring Boot 3.x
- Spring Data JPA
- PostgreSQL
- Maven

## 🚀 Execução

O backend conecta-se ao banco PostgreSQL RDS e expõe a API REST para o frontend.

Consulte o README da raiz para instruções de deploy e configuração.
