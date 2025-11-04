// Configuração da API para produção
const API_CONFIG = {
    // URL do backend no EC2 (será atualizada após deploy)
    BASE_URL: 'http://SEU-EC2-IP:8080', // Substitua pelo IP/domínio do seu EC2
    
    // Endpoints da API
    ENDPOINTS: {
        // Pacientes
        PACIENTES: '/pacientes',
        PACIENTE_BY_ID: '/pacientes',
        ADICIONAR_PACIENTE: '/pacientes/adicionar',
        ATUALIZAR_PACIENTE: '/pacientes/atualizar',
        DELETAR_PACIENTE: '/pacientes/deletar',
        
        // Usuários
        USUARIOS: '/usuarios',
        LOGIN: '/usuarios/login',
        CADASTRO: '/usuarios/cadastrar',
        ATUALIZAR_USUARIO: '/usuarios/atualizar',
        
        // Alertas
        ALERTAS: '/alertas',
        CONFIGURAR_ALERTAS: '/alertas/configurar'
    }
};

// Função utilitária para fazer requisições
window.apiRequest = async function(endpoint, options = {}) {
    const url = `${API_CONFIG.BASE_URL}${endpoint}`;
    
    const defaultOptions = {
        headers: {
            'Content-Type': 'application/json',
        }
    };
    
    const requestOptions = { ...defaultOptions, ...options };
    
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