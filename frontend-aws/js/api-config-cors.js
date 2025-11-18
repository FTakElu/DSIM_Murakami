// Configuração de API - Proxy CORS para resolver Mixed Content
// Frontend HTTPS + Proxy HTTPS + Backend HTTP

const API_CONFIG = {
    // Proxy CORS para resolver Mixed Content (HTTPS frontend → HTTP backend)
  BASE_URL: 'https://api.allorigins.win/raw?url=http://3.237.26.213:8080',
    BACKUP_PROXY: 'https://cors-anywhere.herokuapp.com/http://3.237.26.213:8080',
    DIRECT_HTTP: 'http://3.237.26.213:8080',  // Para uso local/desenvolvimento
    
  
    
    // Endpoints da API (corrigidos para backend real)
    ENDPOINTS: {
        // Pacientes
        PACIENTES: '/api/pacientes',
        PACIENTE_BY_ID: '/api/pacientes', // GET /api/pacientes/{id}
        ADICIONAR_PACIENTE: '/api/pacientes', // POST /api/pacientes
        ATUALIZAR_PACIENTE: '/api/pacientes', // PUT /api/pacientes/{id}
        DELETAR_PACIENTE: '/api/pacientes', // DELETE /api/pacientes/{id}
        
        // Usuários
        USUARIOS: '/api/usuarios',
        LOGIN: '/api/usuarios/login', // POST
        CADASTRO: '/api/usuarios/cadastrar', // POST
        ATUALIZAR_USUARIO: '/api/usuarios', // PUT /{id}
        
        // Alertas
        ALERTAS: '/api/alertas',
        CONFIGURAR_ALERTAS: '/api/alertas'
    }
};

// Sistema Mock completo para desenvolvimento
let mockData = {
    usuarios: [
        {
            id: 1,
            nome: "Administrador",
            email: "admin@dsim.com",
            senha: "admin123",
            ativo: true,
            dataCriacao: "2024-01-01T00:00:00Z",
            dataAtualizacao: new Date().toISOString()
        },
        {
            id: 2,
            nome: "Flávia Takato",
            email: "flaviatakato@gmail.com",
            senha: "123456",
            ativo: true,
            dataCriacao: "2024-01-01T00:00:00Z", 
            dataAtualizacao: new Date().toISOString()
        }
    ],
    pacientes: [
        {
            id: 1,
            nome: "Carlos Eduardo Silva",
            dataNascimento: "1985-03-15",
            genero: "Masculino",
            telefone: "(11) 99999-8888",
            email: "carlos@email.com",
            endereco: "Rua das Flores, 123",
            cidade: "São Paulo",
            estado: "SP",
            cep: "01234-567",
            tipoSanguineo: "O+",
            alergias: "Penicilina",
            condicoesMedicas: "Hipertensão leve",
            medicamentosAtuais: "Losartana 50mg",
            quarto: "101A",
            contatoEmergencial: {
                nome: "Maria Silva",
                parentesco: "Esposa",
                telefone: "(11) 88888-7777",
                email: "maria@email.com"
            },
            sinaisVitais: {
                oxigenio: 98.5,
                temperatura: 36.8,
                batimentos: 75,
                pressaoSistolica: 120,
                pressaoDiastolica: 80,
                frequenciaRespiratoria: 16,
                statusOxigenio: "normal",
                statusTemperatura: "normal",
                statusBatimentos: "normal",
                statusPressao: "normal"
            },
            ativo: true,
            dataCriacao: "2024-01-15T10:30:00Z",
            dataAtualizacao: new Date().toISOString()
        },
        {
            id: 2,
            nome: "Márcia dos Santos",
            dataNascimento: "1992-07-22",
            genero: "Feminino",
            telefone: "(11) 88888-7777",
            email: "marcia@email.com",
            endereco: "Av. Paulista, 456",
            cidade: "São Paulo",
            estado: "SP",
            cep: "01310-100",
            tipoSanguineo: "A+",
            alergias: "Nenhuma conhecida",
            condicoesMedicas: "Diabetes tipo 2",
            medicamentosAtuais: "Metformina 850mg",
            quarto: "102B",
            contatoEmergencial: {
                nome: "João Santos",
                parentesco: "Marido",
                telefone: "(11) 77777-6666",
                email: "joao@email.com"
            },
            sinaisVitais: {
                oxigenio: 95.2,
                temperatura: 37.2,
                batimentos: 90,
                pressaoSistolica: 140,
                pressaoDiastolica: 90,
                frequenciaRespiratoria: 18,
                statusOxigenio: "atencao",
                statusTemperatura: "elevada",
                statusBatimentos: "elevado",
                statusPressao: "alta"
            },
            ativo: true,
            dataCriacao: "2024-02-10T14:20:00Z",
            dataAtualizacao: new Date().toISOString()
        }
    ],
    alertas: []
};

// Função principal com múltiplos proxies CORS e fallback
window.apiRequest = async function(endpoint, options = {}) {
    const backends = [
        { name: 'AllOrigins', url: `https://api.allorigins.win/raw?url=http://44.213.58.90${endpoint}` },
        { name: 'CORS-Anywhere', url: `https://cors-anywhere.herokuapp.com/http://44.213.58.90${endpoint}` },
        { name: 'ThingProxy', url: `https://thingproxy.freeboard.io/fetch/http://44.213.58.90${endpoint}` }
    ];
    
    console.log(`🌐 Tentando conectar ao backend via proxy: ${options.method || 'GET'} ${endpoint}`);
    
    const config = {
        method: options.method || 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            ...(options.headers || {})
        },
        ...options
    };

    // Tentar cada proxy em sequência
    for (const backend of backends) {
        try {
            console.log(`🔄 Tentando ${backend.name}...`);
            const response = await fetch(backend.url, config);
            
            if (!response.ok) {
                throw new Error(`HTTP ${response.status}`);
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
            
            console.log(`✅ ${backend.name} - Sucesso!`);
            return data;
            
        } catch (error) {
            console.warn(`❌ ${backend.name} falhou:`, error.message);
            continue;
        }
    }
    
    // Se todos os proxies falharem, usar mock
    console.error('❌ Todos os proxies falharam. Usando Mock como fallback.');
    throw new Error('Não foi possível conectar ao backend');
};

// Salvar referência original dos proxies
window.apiRequestProxies = window.apiRequest;

// Sistema Mock mantido como fallback
window.apiRequestMock = async function(endpoint, options = {}) {
    // Simular delay de rede
    await new Promise(resolve => setTimeout(resolve, 200));
    
    const method = options.method || 'GET';
    console.log(`🔧 Mock API (Fallback): ${method} ${endpoint}`);
    
    try {
        // LOGIN
        if (endpoint.includes('/api/usuarios/login') && method === 'POST') {
            const body = JSON.parse(options.body);
            console.log('🔐 Tentativa de login mock:', body.email);
            
            const usuario = mockData.usuarios.find(u => 
                u.email === body.email && u.senha === body.senha && u.ativo
            );
            
            if (usuario) {
                const { senha, ...usuarioSemSenha } = usuario;
                console.log('✅ Login bem-sucedido no mock para:', body.email);
                return usuarioSemSenha;
            } else {
                console.log('❌ Credenciais inválidas no mock para:', body.email);
                throw new Error('Credenciais inválidas');
            }
        }
        
        // CADASTRO DE USUÁRIO
        if (endpoint.includes('/api/usuarios/cadastrar') && method === 'POST') {
            const body = JSON.parse(options.body);
            console.log('👤 Cadastro de usuário via mock:', body.email);
            
            // Verificar se email já existe
            const emailExiste = mockData.usuarios.find(u => u.email === body.email);
            if (emailExiste) {
                console.log('⚠️ Email já existe no mock');
                throw new Error('Email já cadastrado');
            }
            
            const novoId = Math.max(...mockData.usuarios.map(u => u.id), 0) + 1;
            const novoUsuario = {
                id: novoId,
                ...body,
                ativo: true,
                dataCriacao: new Date().toISOString(),
                dataAtualizacao: new Date().toISOString()
            };
            mockData.usuarios.push(novoUsuario);
            
            console.log('✅ Usuário cadastrado no mock local');
            console.log('💡 Para acessar o sistema real, aceite certificado NGINX');
            console.log(`📧 Use: ${body.email} / ${body.senha} para fazer login`);
            
            return { success: true, message: `Usuário ${body.email} cadastrado! Você pode fazer login agora.` };
        }
        
        // LISTAR PACIENTES
        if (endpoint === '/api/pacientes' && method === 'GET') {
            console.log('✅ Listando pacientes');
            return mockData.pacientes.filter(p => p.ativo);
        }
        
        // BUSCAR PACIENTE POR ID
        if (endpoint.match(/\/api\/pacientes\/\d+$/) && method === 'GET') {
            const id = parseInt(endpoint.split('/').pop());
            const paciente = mockData.pacientes.find(p => p.id === id && p.ativo);
            if (paciente) {
                console.log('✅ Paciente encontrado');
                return paciente;
            } else {
                throw new Error('Paciente não encontrado');
            }
        }
        
        // ADICIONAR PACIENTE
        if (endpoint.includes('/api/pacientes') && method === 'POST') {
            const body = JSON.parse(options.body);
            const novoId = Math.max(...mockData.pacientes.map(p => p.id)) + 1;
            const novoPaciente = {
                id: novoId,
                ...body,
                ativo: true,
                dataCriacao: new Date().toISOString(),
                dataAtualizacao: new Date().toISOString(),
                sinaisVitais: {
                    oxigenio: 98.0,
                    temperatura: 36.5,
                    batimentos: 72,
                    pressaoSistolica: 120,
                    pressaoDiastolica: 80,
                    frequenciaRespiratoria: 16,
                    statusOxigenio: "normal",
                    statusTemperatura: "normal",
                    statusBatimentos: "normal",
                    statusPressao: "normal"
                }
            };
            mockData.pacientes.push(novoPaciente);
            console.log('✅ Paciente adicionado');
            return { success: true, message: 'Paciente cadastrado com sucesso', id: novoId };
        }
        
        // ATUALIZAR PACIENTE
        if (endpoint.match(/\/api\/pacientes\/\d+$/) && method === 'PUT') {
            const id = parseInt(endpoint.split('/').pop());
            const body = JSON.parse(options.body);
            const index = mockData.pacientes.findIndex(p => p.id === id);
            
            if (index !== -1) {
                mockData.pacientes[index] = {
                    ...mockData.pacientes[index],
                    ...body,
                    id: id,
                    dataAtualizacao: new Date().toISOString()
                };
                console.log('✅ Paciente atualizado');
                return { success: true, message: 'Paciente atualizado com sucesso' };
            } else {
                throw new Error('Paciente não encontrado');
            }
        }
        
        // DELETAR PACIENTE
        if (endpoint.match(/\/api\/pacientes\/\d+$/) && method === 'DELETE') {
            const id = parseInt(endpoint.split('/').pop());
            const index = mockData.pacientes.findIndex(p => p.id === id);
            
            if (index !== -1) {
                mockData.pacientes[index].ativo = false;
                console.log('✅ Paciente excluído');
                return { success: true, message: 'Paciente excluído com sucesso' };
            } else {
                throw new Error('Paciente não encontrado');
            }
        }
        
        // PERFIL DO USUÁRIO
        if (endpoint.includes('/api/usuarios/perfil') && method === 'GET') {
            const url = new URL(`http://localhost${endpoint}`);
            const email = url.searchParams.get('email');
            console.log('👤 Buscando perfil para:', email);
            
            const usuario = mockData.usuarios.find(u => u.email === email && u.ativo);
            if (usuario) {
                const { senha, ...usuarioSemSenha } = usuario;
                console.log('✅ Perfil encontrado:', usuarioSemSenha);
                return usuarioSemSenha;
            } else {
                console.log('❌ Usuário não encontrado:', email);
                throw new Error('Usuário não encontrado');
            }
        }
        
        // LISTAR USUÁRIOS
        if (endpoint === '/api/usuarios' && method === 'GET') {
            console.log('✅ Listando usuários');
            return mockData.usuarios.filter(u => u.ativo).map(u => {
                const { senha, ...usuarioSemSenha } = u;
                return usuarioSemSenha;
            });
        }
        
        // LISTAR PACIENTES
        if (endpoint.includes('/historico-sinais/')) {
            console.log('✅ Histórico de sinais vitais simulado');
            const agora = new Date();
            const historico = [];
            
            // Gerar dados das últimas 24 horas
            for (let i = 23; i >= 0; i--) {
                const hora = new Date(agora.getTime() - (i * 60 * 60 * 1000));
                historico.push({
                    timestamp: hora.toISOString(),
                    oxigenio: 95 + Math.random() * 5,
                    temperatura: 36.0 + Math.random() * 2,
                    batimentos: 60 + Math.random() * 40,
                    pressaoSistolica: 110 + Math.random() * 30,
                    pressaoDiastolica: 70 + Math.random() * 20,
                    frequenciaRespiratoria: 12 + Math.random() * 8
                });
            }
            
            return historico;
        }
        
        // ALERTAS (genérico)
        if (endpoint.includes('/api/alertas')) {
            console.log('✅ Operação de alertas');
            return { success: true, message: 'Operação de alertas simulada', data: mockData.alertas };
        }
        
        // Fallback genérico
        console.log('✅ Operação genérica simulada');
        return { success: true, message: 'Operação simulada com sucesso' };
        
    } catch (error) {
        console.error('❌ Erro no mock:', error.message);
        throw error;
    }
};

// Função principal com fallback automático
window.apiRequestWithFallback = async function(endpoint, options = {}) {
    try {
        // Primeiro tenta os proxies CORS
        return await window.apiRequestProxies(endpoint, options);
    } catch (error) {
        console.error(`❌ Todos os proxies falharam. Usando sistema Mock como fallback.`);
        console.warn(`Erro dos proxies:`, error.message);
        
        // Fallback automático para sistema mock
        return await window.apiRequestMock(endpoint, options);
    }
};

// Redefinir apiRequest para usar a versão com fallback
window.apiRequest = window.apiRequestWithFallback;

// Disponibilizar configuração globalmente
window.API_CONFIG = API_CONFIG;

// Função helper para aceitar certificado NGINX
window.aceitarCertificado = function() {
    console.log('🔐 Abrindo página para aceitar certificado...');
    const newWindow = window.open('https://44.213.58.90/api/usuarios', '_blank');
    
    setTimeout(() => {
        console.log('💡 INSTRUÇÕES:');
        console.log('1. Na nova aba, clique "Avançado"');
        console.log('2. Clique "Continuar para 44.213.58.90 (não seguro)"');
        console.log('3. Feche a aba e volte aqui');
        console.log('4. Tente cadastrar/logar novamente');
    }, 2000);
    
    return 'Certificado sendo configurado...';
};