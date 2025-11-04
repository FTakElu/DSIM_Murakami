// Proxy CORS para resolver Mixed Content
// Use este serviço temporariamente para desenvolvimento

const API_CONFIG = {
    // Usar proxy CORS temporário
    BASE_URL: 'https://cors-anywhere.herokuapp.com/http://54.237.230.21:8080',
    
    // URLs originais (para referência)
    // BASE_URL: 'http://54.237.230.21:8080', // Direto (bloqueado por Mixed Content)
    
    // Endpoints da API
    ENDPOINTS: {
        // Pacientes
        PACIENTES: '/api/pacientes',
        PACIENTE_BY_ID: '/api/pacientes',
        ADICIONAR_PACIENTE: '/api/pacientes/adicionar',
        ATUALIZAR_PACIENTE: '/api/pacientes/atualizar',
        DELETAR_PACIENTE: '/api/pacientes/deletar',
        
        // Usuários
        USUARIOS: '/api/usuarios',
        LOGIN: '/api/usuarios/login',
        CADASTRO: '/api/usuarios/cadastrar',
        ATUALIZAR_USUARIO: '/api/usuarios/atualizar',
        
        // Alertas
        ALERTAS: '/api/alertas',
        CONFIGURAR_ALERTAS: '/api/alertas/configurar'
    }
};

// Função utilitária para fazer requisições
window.apiRequest = async function(endpoint, options = {}) {
    const url = `${API_CONFIG.BASE_URL}${endpoint}`;
    
    const defaultOptions = {
        headers: {
            'Content-Type': 'application/json',
            'X-Requested-With': 'XMLHttpRequest', // Para CORS proxy
        }
    };
    
    // Adicionar header do usuário logado se existir
    const usuarioLogado = sessionStorage.getItem('usuarioLogado');
    if (usuarioLogado) {
        const usuario = JSON.parse(usuarioLogado);
        if (usuario && usuario.email) {
            defaultOptions.headers['X-Usuario-Email'] = usuario.email;
        }
    }
    
    const requestOptions = { ...defaultOptions, ...options };
    
    // Mesclar headers se fornecidos nas opções
    if (options.headers) {
        requestOptions.headers = { ...defaultOptions.headers, ...options.headers };
    }
    
    try {
        const response = await fetch(url, requestOptions);
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        return await response.json();
    } catch (error) {
        console.error('Erro na requisição:', error);
        throw error;
    }
};

// Disponibilizar configuração globalmente
window.API_CONFIG = API_CONFIG;