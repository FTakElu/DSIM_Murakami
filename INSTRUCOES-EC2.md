# INSTRUÇÕES PARA ATUALIZAR O EC2
# Execute estes comandos no seu EC2 para atualizar o backend

## 1. CONECTAR NO EC2
```bash
ssh -i "dsim-keypair-20251109083108.pem" ec2-user@ec2-98-93-94-17.compute-1.amazonaws.com
```

## 2. BAIXAR CÓDIGO ATUALIZADO
```bash
# Navegar para diretório do projeto
cd /home/ec2-user/DSIM_Murakami

# Baixar atualizações do GitHub
git pull origin main

# Verificar se baixou as mudanças
git log --oneline -3
```

## 3. RECOMPILAR O JAR
```bash
# Compilar projeto com Maven
mvn clean package -DskipTests

# Verificar se JAR foi criado
ls -la target/sistema-monitoramento-pacientes-1.0.0-SNAPSHOT.jar
```

## 4. CONFIGURAR VARIÁVEIS DE AMBIENTE
```bash
# Exportar variáveis para PostgreSQL
export DB_HOST="dsim-postgres.cluster-xyz.us-east-1.rds.amazonaws.com"
export DB_NAME="dsim_clean"  
export DB_USER="dsim_admin"
export DB_PASSWORD="DSIMPostgres2025!"
export SPRING_PROFILES_ACTIVE="prod"

# Salvar no profile para persistir
echo 'export SPRING_PROFILES_ACTIVE="prod"' >> ~/.bashrc
echo 'export DB_HOST="dsim-postgres.cluster-xyz.us-east-1.rds.amazonaws.com"' >> ~/.bashrc
echo 'export DB_NAME="dsim_clean"' >> ~/.bashrc  
echo 'export DB_USER="dsim_admin"' >> ~/.bashrc
echo 'export DB_PASSWORD="DSIMPostgres2025!"' >> ~/.bashrc
source ~/.bashrc
```

## 5. EXECUTAR DEPLOY
```bash
# Dar permissão ao script
chmod +x deploy-ec2.sh

# Executar deploy
./deploy-ec2.sh
```

## 6. VERIFICAR APLICAÇÃO
```bash
# Verificar se está rodando
ps aux | grep java

# Ver logs em tempo real
tail -f /var/log/dsim/application.log

# Testar API
curl http://localhost:8080/api/usuarios

# Testar do frontend
curl http://98.93.94.17:8080/api/usuarios
```

## 7. COMANDOS ÚTEIS
```bash
# Parar aplicação
sudo pkill -f "java.*dsim"

# Reiniciar aplicação
./deploy-ec2.sh

# Ver logs dos últimos erros
tail -50 /var/log/dsim/application.log

# Monitorar uso de CPU/Memória
top
htop
```

## ✅ VERIFICAÇÕES FINAIS
- [ ] Aplicação está rodando (processo Java ativo)
- [ ] API responde: `curl http://98.93.94.17:8080/api/usuarios`
- [ ] Logs sem erros de conexão com banco
- [ ] Frontend no Amplify consegue se conectar
- [ ] Cadastro e login funcionando

## 🔧 RESOLUÇÃO DE PROBLEMAS COMUNS

### Se der erro de conexão com PostgreSQL:
```bash
# Verificar conectividade
telnet dsim-postgres.cluster-xyz.us-east-1.rds.amazonaws.com 5432

# Verificar variáveis
echo $DB_HOST
echo $DB_USER
echo $SPRING_PROFILES_ACTIVE
```

### Se der erro de CORS:
- Verificar se as URLs do Amplify estão corretas no WebConfig.java
- Reiniciar aplicação após mudanças

### Se der erro de memória:
```bash
# Verificar memória disponível
free -m

# Reiniciar com mais memória
java -Xmx1024m -jar /opt/dsim/sistema-monitoramento-pacientes.jar
```