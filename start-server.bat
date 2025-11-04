@echo off
echo ================================================
echo   Sistema de Monitoramento de Pacientes - DSIM
echo ================================================
echo.
echo Iniciando o servidor Spring Boot...
echo.

REM Limpar porta se estiver em uso
for /f "tokens=5" %%a in ('netstat -ano ^| find ":8080"') do (
    echo Finalizando processo na porta 8080: %%a
    taskkill /F /PID %%a >nul 2>&1
)

REM Aguardar um momento
timeout /t 2 >nul

REM Iniciar o servidor
echo Executando: mvn spring-boot:run
echo.
mvn spring-boot:run

pause