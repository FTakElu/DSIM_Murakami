# 🚀 GUIA RÁPIDO - Deploy Amplify DSIM

## ✅ STATUS ATUAL
- ✅ GitHub atualizado com novo IP (52.200.154.67)  
- ✅ Backend funcionando 100%
- ✅ Frontend configurado para APIs reais
- ✅ Arquivos BAT removidos

## 🎯 OPÇÕES PARA AMPLIFY

### **OPÇÃO 1: Deploy direto via GitHub (RECOMENDADO)**
1. Acesse: https://console.aws.amazon.com/amplify/
2. Clique em "New app" → "Host web app"
3. Conecte com GitHub: `FTakElu/DSIM_Murakami`
4. Branch: `main`
5. Pasta: `frontend-aws` (já configurado no amplify.yml)
6. Deploy automático!

### **OPÇÃO 2: Via Amplify CLI**
```cmd
# Se você já tem um app Amplify
amplify pull
amplify publish

# Se é novo projeto
amplify init
amplify add hosting
amplify publish
```

### **OPÇÃO 3: Verificar se já existe**
1. Vá em https://console.aws.amazon.com/amplify/
2. Veja se já tem o projeto "DSIM" 
3. Se tiver, só fazer um novo deploy

## 📋 CONFIGURAÇÕES IMPORTANTES

### URLs que serão usadas:
- **Backend**: http://52.200.154.67:8080 ✅
- **Frontend**: https://[sua-url].amplifyapp.com

### Arquivo principal: `amplify.yml`
```yaml
version: 1
frontend:
  phases:
    preBuild:
      commands:
        - echo "Preparando frontend DSIM para deploy"
        - cd frontend-aws
    build:
      commands:
        - echo "Frontend estático - build concluído"
  artifacts:
    baseDirectory: frontend-aws
    files:
      - '**/*'
```

## 🧪 TESTE LOCAL ANTES DO DEPLOY
Abra: `teste-integracao.html` para verificar se APIs funcionam!

## 🎯 CREDENCIAIS PARA TESTE
- Email: `admin@dsim.com`
- Senha: `admin123`

---
**💡 RECOMENDAÇÃO:** Use a Opção 1 (console web) é mais rápido!