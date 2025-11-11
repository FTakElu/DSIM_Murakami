// Configuração de API com NGINX HTTPS Proxy
// NGINX configurado como proxy reverso HTTPS no EC2

const API_CONFIG = {
    // APENAS URLs HTTPS - sem mixed content
    BASE_URL: 'https://54.82.30.167',
    
    // Endpoints da API
    ENDPOINTS: {
        // Pacientes
        PACIENTES: '/api/pacientes',
        PACIENTE_BY_ID: '/api/pacientes',
        ADICIONAR_PACIENTE: '/api/pacientes',
        ATUALIZAR_PACIENTE: '/api/pacientes',
        DELETAR_PACIENTE: '/api/pacientes',
        
        // Usuários
        USUARIOS: '/api/usuarios',
        LOGIN: '/api/usuarios/login',
        CADASTRO: '/api/usuarios/cadastrar',
        ATUALIZAR_USUARIO: '/api/usuarios',
        
        // Alertas
        ALERTAS: '/api/alertas',
        CONFIGURAR_ALERTAS: '/api/alertas'
    }
};

// Função para API PostgreSQL RDS via NGINX HTTPS
window.apiRequest = async function(endpoint, options = {}) {
    // APENAS NGINX HTTPS - Spring Boot gerencia CORS
    const url = `https://54.82.30.167${endpoint}`;
    
    console.log(`🌐 PostgreSQL RDS via NGINX: ${options.method || 'GET'} ${url}`);
    
    const config = {
        method: options.method || 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            ...(options.headers || {})
        },
        ...(options.body && { body: options.body }),  // Só adiciona body se existir
        ...options
    };
    
    try {
        const response = await fetch(url, config);
        
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`HTTP ${response.status}: ${errorText || response.statusText}`);
        }
        
        let data;
        const contentType = response.headers.get('content-type');
        
        if (contentType && contentType.includes('application/json')) {
            data = await response.json();
        } else {
            const text = await response.text();
            try {
                data = JSON.parse(text);
            } catch {
                data = { message: text };
            }
        }
        
        console.log(`✅ PostgreSQL RDS - Sucesso!`);
        return data;
        
    } catch (error) {
        console.error(`❌ PostgreSQL RDS - Falhou:`, error.message);
        
        if (error.message.includes('ERR_CERT_AUTHORITY_INVALID')) {
            console.error('🔐 CERTIFICADO NGINX NECESSÁRIO!');
            console.error('1. Abra: https://54.82.30.167/api/usuarios');
            console.error('2. Clique "Avançado" → "Continuar"');
            console.error('3. Volte e tente novamente');
            throw new Error('ACEITE O CERTIFICADO NGINX: https://54.82.30.167/api/usuarios');
        }
        
        throw error;
    }
};

// Disponibilizar configuração globalmente
window.API_CONFIG = API_CONFIG;

// Função helper para aceitar certificado NGINX
window.aceitarCertificado = function() {
    console.log('🔐 Abrindo página para aceitar certificado...');
    const newWindow = window.open('https://54.82.30.167/api/usuarios', '_blank');
    
    setTimeout(() => {
        console.log('💡 INSTRUÇÕES:');
        console.log('1. Na nova aba, clique "Avançado"');
        console.log('2. Clique "Continuar para 54.82.30.167 (não seguro)"');
        console.log('3. Feche a aba e volte aqui');
        console.log('4. Tente cadastrar/logar novamente');
    }, 2000);
    
    return 'Certificado sendo configurado...';
};