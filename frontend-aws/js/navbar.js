/* ===== COMPONENTE NAVBAR ===== */
/* Arquivo: navbar.js - JavaScript para navbar reutilizável */

// Função para criar navbar dinâmico
function createNavbar(currentPage, pageTitle) {
    const navbarHTML = `
        <nav class="main-navbar">
            <div class="navbar-content">
                <a href="../index.html" class="navbar-brand">
                    <i class="fas fa-heartbeat"></i>
                    DSIM
                </a>
                <h1 class="navbar-title">${pageTitle}</h1>
                <div class="navbar-actions">
                    ${getNavbarButtons(currentPage)}
                </div>
            </div>
        </nav>
    `;
    return navbarHTML;
}

// Função para definir os botões baseado na página atual
function getNavbarButtons(currentPage) {
    const buttons = {
        'pacientes': `
            <a href="usuarios.html" class="btn btn-light">
                <i class="fas fa-users"></i>
                Usuários
            </a>
            <a href="cadastrar-paciente.html" class="btn btn-primary">
                <i class="fas fa-plus"></i>
                Novo Paciente
            </a>
        `,
        'usuarios': `
            <a href="visualizar-painel-pacientes.html" class="btn btn-light">
                <i class="fas fa-chart-line"></i>
                Dashboard
            </a>
            <a href="cadastrar-paciente.html" class="btn btn-primary">
                <i class="fas fa-plus"></i>
                Novo Paciente
            </a>
        `,
        'adicionar-paciente': `
            <a href="visualizar-painel-pacientes.html" class="btn btn-light">
                <i class="fas fa-chart-line"></i>
                Dashboard
            </a>
            <a href="usuarios.html" class="btn btn-secondary">
                <i class="fas fa-users"></i>
                Usuários
            </a>
        `,
        'detalhes-paciente': `
            <a href="visualizar-painel-pacientes.html" class="btn btn-light">
                <i class="fas fa-arrow-left"></i>
                Voltar
            </a>
            <button onclick="configurarAlertas()" class="btn btn-warning config-alert-button">
                <i class="fas fa-bell"></i>
                Configurar Alertas
            </button>
        `,
        'configurar-alertas': `
            <a href="visualizar-painel-pacientes.html" class="btn btn-light">
                <i class="fas fa-chart-line"></i>
                Dashboard
            </a>
            <button onclick="voltarDetalhes()" class="btn btn-secondary">
                <i class="fas fa-arrow-left"></i>
                Voltar aos Detalhes
            </button>
        `,
        'login': `
            <a href="../index.html" class="btn btn-light">
                <i class="fas fa-home"></i>
                Início
            </a>
        `,
        'cadastro': `
            <a href="login.html" class="btn btn-light">
                <i class="fas fa-sign-in-alt"></i>
                Login
            </a>
        `
    };
    
    return buttons[currentPage] || '';
}

// Função para inserir navbar no início da página
function insertNavbar(currentPage, pageTitle) {
    const navbar = createNavbar(currentPage, pageTitle);
    document.body.insertAdjacentHTML('afterbegin', navbar);
}

// Função para inicializar navbar automaticamente
function initNavbar() {
    // Detectar página atual pelo nome do arquivo
    const currentFile = window.location.pathname.split('/').pop().replace('.html', '');
    const pageTitles = {
        'pacientes': 'Dashboard de Pacientes',
        'usuarios': 'Gerenciar Usuários', 
        'adicionar-paciente': 'Novo Paciente',
        'detalhes-paciente': 'Detalhes do Paciente',
        'configurar-alertas': 'Configurar Alertas',
        'login': 'Login',
        'cadastro': 'Cadastro'
    };
    
    const pageTitle = pageTitles[currentFile] || 'DSIM - Sistema de Monitoramento';
    insertNavbar(currentFile, pageTitle);
}

// Auto-inicialização se o DOM estiver carregado
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', initNavbar);
} else {
    initNavbar();
}