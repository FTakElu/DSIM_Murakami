# 🚀 Configurar AWS API Gateway para DSIM

## ⚡ Solução Definitiva para Mixed Content (HTTPS → HTTP)

Este guia mostra como configurar um AWS API Gateway para criar um proxy HTTPS que resolve o problema de Mixed Content entre o frontend Amplify (HTTPS) e backend EC2 (HTTP).

## 🎯 **Passo a Passo**

### **1. 📋 Acessar AWS Console**
1. Acesse [AWS Console](https://console.aws.amazon.com/)
2. Entre na região **us-east-1** (Virginia)
3. Procure por **API Gateway**

### **2. 🔧 Criar REST API**
1. Clique em **Create API**
2. Escolha **REST API** → **Build**
3. Configuração:
   - **API name**: `dsim-backend-proxy`
   - **Description**: `Proxy HTTPS para backend DSIM EC2`
   - **Endpoint Type**: `Regional`
4. Clique **Create API**

### **3. 📡 Configurar Resource**
1. Na tela principal da API, clique **Actions** → **Create Resource**
2. Configure:
   - **Resource Name**: `proxy`
   - **Resource Path**: `/{proxy+}`
   - ✅ Marque **Enable API Gateway CORS**
   - ✅ Marque **Configure as proxy resource**
3. Clique **Create Resource**

### **4. 🔄 Configurar Method**
1. Selecione o resource `/{proxy+}` criado
2. Clique **Actions** → **Create Method**
3. Selecione **ANY** no dropdown e clique ✅
4. Configure:
   - **Integration type**: `HTTP Proxy`
   - **HTTP method**: `ANY`
   - **Endpoint URL**: `http://54.82.30.167:8080/{proxy}`
   - ✅ Marque **Use Default Timeout**
5. Clique **Save**

### **5. 🌐 Configurar CORS**
1. Selecione o resource `/{proxy+}`
2. Clique **Actions** → **Enable CORS**
3. Configure:
   - **Access-Control-Allow-Origin**: `*`
   - **Access-Control-Allow-Headers**: `Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token`
   - **Access-Control-Allow-Methods**: `GET,POST,PUT,DELETE,OPTIONS`
4. Clique **Enable CORS and replace existing CORS headers**
5. Confirme clicando **Yes, replace existing values**

### **6. 🚀 Deploy da API**
1. Clique **Actions** → **Deploy API**
2. Configure:
   - **Deployment stage**: `[New Stage]`
   - **Stage name**: `prod`
   - **Stage description**: `Produção DSIM`
3. Clique **Deploy**

### **7. 📋 Copiar URL da API**
Após o deploy, você receberá uma URL como:
```
https://xxxxxxxxxx.execute-api.us-east-1.amazonaws.com/prod
```

### **8. 🔧 Atualizar Frontend**
Edite o arquivo `frontend-aws/js/api-config-cors.js`:

```javascript
const API_CONFIG = {
    // URL do API Gateway (substitua pela sua URL)
    BASE_URL: 'https://xxxxxxxxxx.execute-api.us-east-1.amazonaws.com/prod',
    
    // Remover proxies desnecessários
    // BACKUP_PROXY: '...',
    
    // URL direta como fallback
    FALLBACK_URL: 'http://54.82.30.167:8080',
    
    // Endpoints permanecem os mesmos
    ENDPOINTS: {
        // ... (manter como está)
    }
};
```

## ✅ **Verificar Funcionamento**

### **Testar via Browser:**
```
https://sua-api-gateway.execute-api.us-east-1.amazonaws.com/prod/api/usuarios
```

### **Testar via Curl:**
```bash
curl https://sua-api-gateway.execute-api.us-east-1.amazonaws.com/prod/api/usuarios
```

## 🎯 **Resultado Esperado**
- ✅ Frontend HTTPS conecta ao API Gateway HTTPS
- ✅ API Gateway faz proxy para backend EC2 HTTP
- ✅ Mixed Content resolvido
- ✅ Sistema mock removido
- ✅ Comunicação 100% com backend real

## 🔧 **Troubleshooting**

### **Erro 403 Forbidden:**
- Verifique se CORS está habilitado
- Confirme se o método ANY está configurado

### **Erro 502 Bad Gateway:**
- Verifique se EC2 está rodando na porta 8080
- Confirme se o IP 54.82.30.167 está correto

### **Erro de CORS:**
- Re-configure CORS seguindo o passo 5
- Faça novo deploy da API

## ⏱️ **Tempo Estimado**
- **Configuração**: 10-15 minutos
- **Teste**: 5 minutos
- **Atualização do código**: 2 minutos

---

**🎉 Depois de configurado, o sistema funcionará 100% com o backend real em PostgreSQL!**