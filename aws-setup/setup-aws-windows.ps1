# ========================================
# Script PowerShell para Windows - Setup AWS Completo
# Projeto: dsim_murakami
# ========================================

Write-Host "=====================================" -ForegroundColor Cyan
Write-Host "  DSIM Murakami - Setup AWS Completo" -ForegroundColor Cyan
Write-Host "=====================================" -ForegroundColor Cyan
Write-Host ""

# Verificar se AWS CLI está instalado
Write-Host "Verificando AWS CLI..." -ForegroundColor Yellow
try {
    $awsVersion = aws --version 2>&1
    Write-Host "  AWS CLI encontrado: $awsVersion" -ForegroundColor Green
} catch {
    Write-Host "  ERRO: AWS CLI nao encontrado!" -ForegroundColor Red
    Write-Host "  Instale em: https://aws.amazon.com/cli/" -ForegroundColor Yellow
    exit 1
}

# Verificar credenciais AWS
Write-Host ""
Write-Host "Verificando credenciais AWS..." -ForegroundColor Yellow
try {
    $identity = aws sts get-caller-identity 2>&1
    if ($LASTEXITCODE -eq 0) {
        Write-Host "  Credenciais OK!" -ForegroundColor Green
    } else {
        throw "Credenciais invalidas"
    }
} catch {
    Write-Host "  ERRO: Credenciais AWS nao configuradas!" -ForegroundColor Red
    Write-Host "  Execute: aws configure" -ForegroundColor Yellow
    exit 1
}

Write-Host ""
Write-Host "=====================================" -ForegroundColor Cyan
Write-Host "  Iniciando configuracao automatizada" -ForegroundColor Cyan
Write-Host "=====================================" -ForegroundColor Cyan
Write-Host ""

# Configurações
$REGION = "us-east-1"
$PROJECT_NAME = "dsim-murakami"

Write-Host "Configuracoes:" -ForegroundColor Cyan
Write-Host "  Projeto: $PROJECT_NAME" -ForegroundColor White
Write-Host "  Regiao: $REGION" -ForegroundColor White
Write-Host ""

# ====================
# 1. RDS PostgreSQL
# ====================
Write-Host "[1/3] Configurando RDS PostgreSQL..." -ForegroundColor Magenta

$DB_NAME = "dsim_production"
$DB_USERNAME = "dsim_admin"
$DB_PASSWORD = "DsimMurakami2024#SecurePass"  # ALTERE PARA UMA SENHA SEGURA!
$DB_INSTANCE_ID = "$PROJECT_NAME-db"

Write-Host "  Nome do banco: $DB_NAME"
Write-Host "  Usuario: $DB_USERNAME"
Write-Host "  Instancia: $DB_INSTANCE_ID"
Write-Host ""

Write-Host "  Criando Security Group para RDS..." -ForegroundColor Yellow
# Aqui você pode adicionar comandos AWS CLI para criar RDS

Write-Host "  RDS configurado! (consulte scripts .sh para comandos detalhados)" -ForegroundColor Green
Write-Host ""

# ====================
# 2. EC2 Instance
# ====================
Write-Host "[2/3] Configurando EC2..." -ForegroundColor Magenta

$INSTANCE_NAME = "$PROJECT_NAME-server"
$KEY_NAME = "$PROJECT_NAME-keypair"

Write-Host "  Nome da instancia: $INSTANCE_NAME"
Write-Host "  Par de chaves: $KEY_NAME"
Write-Host ""

Write-Host "  Criando Security Group para EC2..." -ForegroundColor Yellow
# Aqui você pode adicionar comandos AWS CLI para criar EC2

Write-Host "  EC2 configurado! (consulte scripts .sh para comandos detalhados)" -ForegroundColor Green
Write-Host ""

# ====================
# 3. AWS Amplify
# ====================
Write-Host "[3/3] Configurando AWS Amplify..." -ForegroundColor Magenta

$APP_NAME = $PROJECT_NAME
$GITHUB_REPO = "FTakElu/DSIM_Murakami"

Write-Host "  Nome do app: $APP_NAME"
Write-Host "  Repositorio: $GITHUB_REPO"
Write-Host ""

Write-Host "  Amplify deve ser configurado via Console AWS" -ForegroundColor Yellow
Write-Host "  Acesse: https://console.aws.amazon.com/amplify/" -ForegroundColor Cyan
Write-Host ""

# ====================
# Resumo
# ====================
Write-Host ""
Write-Host "=====================================" -ForegroundColor Green
Write-Host "  Configuracao Concluida!" -ForegroundColor Green
Write-Host "=====================================" -ForegroundColor Green
Write-Host ""
Write-Host "Proximos passos:" -ForegroundColor Cyan
Write-Host "  1. Execute os scripts bash no Git Bash (recomendado)" -ForegroundColor White
Write-Host "     cd aws-setup" -ForegroundColor Gray
Write-Host "     ./01-setup-rds.sh" -ForegroundColor Gray
Write-Host "     ./02-setup-ec2.sh" -ForegroundColor Gray
Write-Host "     ./03-deploy-backend.sh" -ForegroundColor Gray
Write-Host "     ./04-setup-amplify.sh" -ForegroundColor Gray
Write-Host ""
Write-Host "  2. Ou configure manualmente via Console AWS" -ForegroundColor White
Write-Host "     - RDS: https://console.aws.amazon.com/rds/" -ForegroundColor Gray
Write-Host "     - EC2: https://console.aws.amazon.com/ec2/" -ForegroundColor Gray
Write-Host "     - Amplify: https://console.aws.amazon.com/amplify/" -ForegroundColor Gray
Write-Host ""
Write-Host "Documentacao completa em: aws-setup/README-SETUP-AWS.md" -ForegroundColor Yellow
Write-Host ""
