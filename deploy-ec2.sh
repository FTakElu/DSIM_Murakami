#!/bin/bash
# Script de configuração do servidor EC2 para DSIM Backend

echo "=== CONFIGURANDO SERVIDOR DSIM BACKEND ==="

# 1. Atualizar sistema
echo "📦 Atualizando sistema..."
sudo yum update -y

# 2. Instalar Java 21
echo "☕ Instalando Java 21..."
sudo yum install -y java-21-amazon-corretto

# 3. Verificar instalação do Java
echo "🔍 Verificando Java..."
java -version

# 4. Instalar Git
echo "📚 Instalando Git..."
sudo yum install -y git

# 5. Criar diretório da aplicação
echo "📁 Criando diretórios..."
sudo mkdir -p /opt/dsim
sudo chown ec2-user:ec2-user /opt/dsim
cd /opt/dsim

# 6. Baixar aplicação do GitHub
echo "⬇️ Baixando aplicação..."
git clone https://github.com/FTakElu/DSIM_Murakami.git .

# 7. Dar permissões
chmod +x target/*.jar

# 8. Criar script de inicialização
echo "🚀 Criando script de inicialização..."
cat > /opt/dsim/start-dsim.sh << 'EOF'
#!/bin/bash
cd /opt/dsim
nohup java -jar target/sistema-monitoramento-pacientes-1.0.0-SNAPSHOT.jar > app.log 2>&1 &
echo $! > app.pid
echo "DSIM Backend iniciado! PID: $(cat app.pid)"
EOF

chmod +x /opt/dsim/start-dsim.sh

# 9. Criar script de parada
cat > /opt/dsim/stop-dsim.sh << 'EOF'
#!/bin/bash
if [ -f /opt/dsim/app.pid ]; then
    PID=$(cat /opt/dsim/app.pid)
    kill $PID
    rm -f /opt/dsim/app.pid
    echo "DSIM Backend parado!"
else
    echo "DSIM Backend não estava rodando"
fi
EOF

chmod +x /opt/dsim/stop-dsim.sh

# 10. Criar serviço systemd
echo "⚙️ Configurando serviço systemd..."
sudo tee /etc/systemd/system/dsim.service > /dev/null << 'EOF'
[Unit]
Description=DSIM Backend Service
After=network.target

[Service]
Type=simple
User=ec2-user
WorkingDirectory=/opt/dsim
ExecStart=/usr/bin/java -jar /opt/dsim/target/sistema-monitoramento-pacientes-1.0.0-SNAPSHOT.jar
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
EOF

# 11. Habilitar e iniciar serviço
sudo systemctl daemon-reload
sudo systemctl enable dsim
sudo systemctl start dsim

# 12. Verificar status
echo "✅ Verificando status do serviço..."
sudo systemctl status dsim

echo "🎉 Configuração concluída!"
echo "📡 Aplicação rodando na porta 8080"
echo "🔍 Para verificar logs: sudo journalctl -u dsim -f"
echo "🔄 Para reiniciar: sudo systemctl restart dsim"