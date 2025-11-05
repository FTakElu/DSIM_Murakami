// Proxy CORS para resolver Mixed Content
// Use este serviço temporariamente para desenvolvimento

const API_CONFIG = {
    // Tentar direto primeiro (pode funcionar em alguns casos)
    BASE_URL: 'http://18.232.149.49:8080',
    
    // Alternativas se não funcionar
    // BASE_URL: 'https://api.allorigins.win/raw?url=http://18.232.149.49:8080', 
    // BASE_URL: 'https://cors-anywhere.herokuapp.com/http://18.232.149.49:8080',
    
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

// Função utilitária para fazer requisições com fallback
window.apiRequest = async function(endpoint, options = {}) {
    // Tentar diferentes proxies
    const baseUrls = [
        'http://18.232.149.49:8080', // Direto
        'https://api.allorigins.win/raw?url=http://18.232.149.49:8080', // Proxy AllOrigins
        'https://corsproxy.io/?http://18.232.149.49:8080', // Proxy alternativo
    ];
    
    const defaultOptions = {
        mode: 'cors',
        headers: {
            'Content-Type': 'application/json',
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
    
    // Tentar cada URL até uma funcionar
    for (let i = 0; i < baseUrls.length; i++) {
        const url = `${baseUrls[i]}${endpoint}`;
        
        try {
            console.log(`Tentando: ${url}`);
            const response = await fetch(url, requestOptions);
            
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            
            const data = await response.json();
            console.log(`Sucesso com: ${baseUrls[i]}`);
            return data;
            
        } catch (error) {
            console.log(`Falhou ${baseUrls[i]}:`, error.message);
            
            // Se for a última tentativa, lançar o erro
            if (i === baseUrls.length - 1) {
                console.error('Todas as tentativas falharam:', error);
                throw error;
            }
        }
    }
};

// Disponibilizar configuração globalmente
window.API_CONFIG = API_CONFIG;