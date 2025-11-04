@echo off
echo Atualizando URLs das APIs para configuracao dinamica...

REM Adicionar script de configuracao em todas as páginas HTML
for %%f in (pages\*.html) do (
    echo Processando %%f...
    
    REM Verificar se já tem o script
    findstr /C:"api-config.js" "%%f" >nul
    if errorlevel 1 (
        echo Adicionando script de configuracao em %%f
        REM Aqui você adicionaria o script se não existir
    )
    
    REM Substituir URLs relativas por URLs usando API_CONFIG
    powershell -Command "(gc '%%f') -replace \"fetch\('/api/\", \"fetch(`${API_CONFIG.BASE_URL}/api/\" | Out-File -encoding UTF8 '%%f'"
    powershell -Command "(gc '%%f') -replace \"fetch\(`/api/\", \"fetch(`${API_CONFIG.BASE_URL}/api/\" | Out-File -encoding UTF8 '%%f'"
)

echo Configuracao concluida!
pause