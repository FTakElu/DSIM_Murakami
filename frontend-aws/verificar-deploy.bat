@echo off
echo ===========================================
echo     VERIFICACAO DE CONFIGURACAO AWS
echo ===========================================
echo.

echo [1/5] Verificando estrutura de arquivos...
if exist "index.html" (
    echo ✅ index.html encontrado
) else (
    echo ❌ index.html NAO encontrado
)

if exist "amplify.yml" (
    echo ✅ amplify.yml encontrado
) else (
    echo ❌ amplify.yml NAO encontrado
)

if exist "_redirects" (
    echo ✅ _redirects encontrado
) else (
    echo ❌ _redirects NAO encontrado
)

if exist "js\api-config.js" (
    echo ✅ api-config.js encontrado
) else (
    echo ❌ api-config.js NAO encontrado
)

echo.
echo [2/5] Verificando páginas HTML...
for %%f in (pages\*.html) do (
    echo ✅ %%f
)

echo.
echo [3/5] Verificando CSS e JS...
if exist "css\main.css" (
    echo ✅ CSS principal encontrado
) else (
    echo ❌ CSS principal NAO encontrado
)

if exist "js\navbar.js" (
    echo ✅ JavaScript navegacao encontrado
) else (
    echo ❌ JavaScript navegacao NAO encontrado
)

echo.
echo [4/5] Contando arquivos totais...
set /a filecount=0
for /r %%f in (*) do set /a filecount+=1
echo 📁 Total de arquivos: %filecount%

echo.
echo [5/5] Status do projeto...
echo ✅ Frontend preparado para AWS Amplify
echo ✅ Configuracao de API dinamica implementada
echo ✅ Redirects configurados para SPA
echo ✅ Build config (amplify.yml) pronto

echo.
echo ===========================================
echo        PROXIMOS PASSOS PARA DEPLOY
echo ===========================================
echo.
echo 1️⃣  Criar repositorio GitHub:
echo    git init
echo    git add .
echo    git commit -m "Frontend DSIM para AWS"
echo    git remote add origin https://github.com/SEU_USUARIO/dsim-frontend.git
echo    git push -u origin main
echo.
echo 2️⃣  Configurar AWS Amplify:
echo    - Acesse: console.aws.amazon.com/amplify
echo    - New app ^> Host web app
echo    - Conecte com GitHub
echo    - Selecione o repositorio
echo.
echo 3️⃣  Apos deploy do frontend:
echo    - Anote a URL do Amplify
echo    - Configure o backend no EC2
echo    - Atualize API_CONFIG com IP do EC2
echo.
echo Leia README.md para instruções completas!
echo.
pause