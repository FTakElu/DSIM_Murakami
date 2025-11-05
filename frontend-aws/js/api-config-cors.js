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

// Função utilitária com fallback para diferentes métodos
window.apiRequest = async function(endpoint, options = {}) {
    const EC2_BASE = 'http://18.232.149.49:8080';
    
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
    
    // SOLUÇÃO TEMPORÁRIA: Mock de dados para desenvolvimento
    // Enquanto resolvemos o problema CORS/Mixed Content
    
    console.log(`🔧 Simulando API call para: ${endpoint}`);
    
    // Simular dados baseados no endpoint
    if (endpoint.includes('/api/usuarios/login')) {
        console.log(`✅ Mock: Login simulado com sucesso`);
        
        // Simular resposta de login bem-sucedida
        return {
            success: true,
            message: "Login realizado com sucesso (MOCK)",
            usuario: {
                id: 1,
                nome: "Administrador Sistema (DEMO)",
                email: "admin@sistema.com",
                ativo: true,
                dataCriacao: new Date().toISOString(),
                dataAtualizacao: new Date().toISOString()
            }
        };
    }
    
    if (endpoint.includes('/api/pacientes')) {
        console.log(`✅ Mock: Dados de pacientes simulados`);
        
        return [
            {
                id: 1,
                nome: "Carlos Eduardo Silva (DEMO)",
                dataNascimento: "1985-03-15",
                genero: "Homem",
                telefone: "(11) 99999-8888",
                sinaisVitais: {
                    oxigenio: 98.5,
                    temperatura: 36.8,
                    batimentos: 75,
                    statusOxigenio: "stable",
                    statusTemperatura: "stable",
                    statusBatimentos: "stable"
                }
            },
            {
                id: 2,
                nome: "Márcia dos Santos (DEMO)",
                dataNascimento: "1992-07-22",
                genero: "Mulher", 
                telefone: "(11) 88888-7777",
                sinaisVitais: {
                    oxigenio: 95.2,
                    temperatura: 37.2,
                    batimentos: 90,
                    statusOxigenio: "warning",
                    statusTemperatura: "warning", 
                    statusBatimentos: "warning"
                }
            }
        ];
    }
    
    // Para outros endpoints, retornar sucesso genérico
    console.log(`✅ Mock: Operação simulada com sucesso`);
    return { success: true, message: "Operação simulada (MODO DEMO)" };
};

// Disponibilizar configuração globalmente
window.API_CONFIG = API_CONFIG;