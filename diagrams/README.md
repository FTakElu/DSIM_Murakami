# Diagramas do Projeto DSIM Murakami

Este diretório contém diagramas de arquitetura, fluxo e entidades do sistema.

## 📁 Arquivos

- **AWS.drawio.png**: Diagrama de infraestrutura AWS (EC2, RDS, Amplify)
- **Diagramas.asta**: Diagrama UML de casos de uso e entidades (Astah)

## 📊 Casos de Uso Implementados

| ID | Caso de Uso | Arquivo Frontend | Controller Backend |
|----|-------------|------------------|-------------------|
| UC01 | Cadastrar Usuário | `cadastrar-usuario.html` | ManterUsuarioController |
| UC02 | Autenticar Usuário | `login.html` | ManterUsuarioController |
| UC03 | Cadastrar Paciente | `cadastrar-paciente.html` | ManterPacienteController |
| UC04 | Configurar Alertas | `configurar-alertas.html` | ManterConfiguracaoAlertaController |
| UC05 | Visualizar Painel | `visualizar-painel-pacientes.html` | ManterPacienteController |
| UC06 | Visualizar Informações | `visualizar-informacoes-paciente.html` | ManterPacienteController |

## 🏗️ Entidades do Sistema

### Principais
- **Paciente**: Dados do paciente (nome, CPF, data nascimento, etc)
  - Relacionamento 1:1 obrigatório com InformacaoMedica
  - Relacionamento 1:1 obrigatório com ContatoEmergencial
  - Relacionamento 1:N com SinaisVitais

- **Usuario**: Usuários do sistema (admin, médicos)
- **Alerta**: Alertas gerados pelo sistema
- **ConfiguracaoAlerta**: Configurações de alertas personalizadas
- **SinaisVitais**: Dados de sinais vitais (oxigênio, temperatura, batimentos)

### Enumerações
- **TipoAlerta**: OXIGENACAO, TEMPERATURA, BATIMENTOS_CARDIACOS
- **NivelPrioridade**: BAIXA, MEDIA, ALTA
- **StatusSinal**: NORMAL, ALTO, BAIXO

## 🔧 Ferramentas

Para visualizar os diagramas:
- **Astah**: Para abrir `Diagramas.asta` (UML)
- **Draw.io**: Para visualizar `AWS.drawio.png` (Infraestrutura)

Utilize estes arquivos para entender a estrutura do projeto e suas dependências.
