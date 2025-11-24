# Frontend AWS - DSIM Murakami# Frontend AWS - DSIM Murakami



Este diretório contém o frontend do sistema, pronto para deploy no AWS Amplify.Este diretório contém o frontend do sistema, pronto para deploy no AWS Amplify.



## 📁 Estrutura## Estrutura

- `index.html`: Página principal

```- `js/`: Scripts JavaScript (API, navegação, etc)

frontend-aws/- `css/`: Estilos

├── index.html                                  # Página inicial- `pages/`: Páginas do sistema

├── _redirects                                  # Regras de redirecionamento Amplify

├── test-api.html                               # Testes de API## Deploy

├── assets/css/O deploy é feito via AWS Amplify, apontando para o backend EC2/RDS.

│   └── style.css                               # Estilos unificados

├── js/Consulte o README da raiz para o passo a passo completo.
│   ├── api-config.js                           # Configuração API
│   ├── navbar.js                               # Menu de navegação
│   └── navigation-fix.js                       # Correções de navegação
└── pages/                                      # Páginas do sistema
    ├── cadastrar-usuario.html                  # UC01 - Cadastrar Usuário
    ├── login.html                              # UC02 - Autenticar Usuário
    ├── cadastrar-paciente.html                 # UC03 - Cadastrar Paciente
    ├── configurar-alertas.html                 # UC04 - Configurar Alertas
    ├── visualizar-painel-pacientes.html        # UC05 - Visualizar Painel
    ├── visualizar-informacoes-paciente.html    # UC06 - Informações Paciente
    └── visualizar-painel-usuarios.html         # Admin - Gerenciar Usuários
```

## 🎯 Nomenclatura das Páginas

As páginas seguem a nomenclatura dos **Casos de Uso** do sistema:

| Arquivo | Caso de Uso | Descrição |
|---------|-------------|-----------|
| `cadastrar-usuario.html` | UC01 | Formulário de cadastro de novos usuários |
| `login.html` | UC02 | Tela de autenticação |
| `cadastrar-paciente.html` | UC03 | Formulário de cadastro de pacientes |
| `configurar-alertas.html` | UC04 | Configuração de alertas do sistema |
| `visualizar-painel-pacientes.html` | UC05 | Dashboard com lista de pacientes |
| `visualizar-informacoes-paciente.html` | UC06 | Detalhes completos do paciente |
| `visualizar-painel-usuarios.html` | Admin | Gerenciamento de usuários (CRUD) |

## 🔧 Tecnologias

- HTML5
- CSS3 (Bootstrap 5)
- JavaScript (Vanilla)
- Font Awesome 6
- API REST (fetch)

## 🚀 Deploy

O deploy é feito via **AWS Amplify**, apontando para o backend EC2/RDS.

### Configuração API

Edite `js/api-config.js` para apontar para o backend:

```javascript
const API_BASE_URL = 'http://SEU-IP-EC2:8080/api';
```

Consulte o README da raiz para o passo a passo completo.
