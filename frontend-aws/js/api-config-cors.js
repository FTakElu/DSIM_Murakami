// Proxy CORS para resolver Mixed Content
// Use este serviço temporariamente para desenvolvimento

const API_CONFIG = {
    // Novo IP do EC2 AWS - atualizado automaticamente
    BASE_URL: 'http://3.88.99.86:8080',
    
    // Alternativas se não funcionar
    // BASE_URL: 'https://api.allorigins.win/raw?url=http://3.88.99.86:8080', 
    // BASE_URL: 'https://cors-anywhere.herokuapp.com/http://3.88.99.86:8080',
    
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

// Sistema Mock completo para desenvolvimento
let mockData = {
    usuarios: [
        {
            id: 1,
            nome: "Administrador Sistema",
            email: "admin@sistema.com",
            senha: "admin123",
            tipo: "ADMIN",
            ativo: true,
            dataCriacao: new Date().toISOString(),
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

// Função utilitária com sistema mock completo
window.apiRequest = async function(endpoint, options = {}) {
    // Simular delay de rede
    await new Promise(resolve => setTimeout(resolve, 200));
    
    const method = options.method || 'GET';
    console.log(`🔧 Mock API: ${method} ${endpoint}`);
    
    try {
        // LOGIN
        if (endpoint.includes('/api/usuarios/login') && method === 'POST') {
            const body = JSON.parse(options.body);
            const usuario = mockData.usuarios.find(u => 
                u.email === body.email && u.senha === body.senha
            );
            
            if (usuario) {
                const { senha, ...usuarioSemSenha } = usuario;
                console.log('✅ Login bem-sucedido');
                return usuarioSemSenha;
            } else {
                throw new Error('Credenciais inválidas');
            }
        }
        
        // CADASTRO DE USUÁRIO
        if (endpoint.includes('/api/usuarios/cadastrar') && method === 'POST') {
            const body = JSON.parse(options.body);
            const novoId = Math.max(...mockData.usuarios.map(u => u.id)) + 1;
            const novoUsuario = {
                id: novoId,
                ...body,
                ativo: true,
                dataCriacao: new Date().toISOString(),
                dataAtualizacao: new Date().toISOString()
            };
            mockData.usuarios.push(novoUsuario);
            console.log('✅ Usuário cadastrado');
            return { success: true, message: 'Usuário cadastrado com sucesso' };
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
        
        // LISTAR USUÁRIOS
        if (endpoint === '/api/usuarios' && method === 'GET') {
            console.log('✅ Listando usuários');
            return mockData.usuarios.filter(u => u.ativo).map(u => {
                const { senha, ...usuarioSemSenha } = u;
                return usuarioSemSenha;
            });
        }
        
        // HISTÓRICO DE SINAIS VITAIS
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

// Disponibilizar configuração globalmente
window.API_CONFIG = API_CONFIG;