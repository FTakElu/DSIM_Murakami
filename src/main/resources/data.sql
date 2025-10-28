-- Dados iniciais para teste

-- Usuários do sistema
INSERT INTO usuarios (nome, email, senha, cargo, ativo, data_criacao, data_atualizacao) VALUES 
('Administrador Sistema', 'admin@sistema.com', 'senha123', 'ADMINISTRADOR', true, NOW(), NOW()),
('Dr. Joao Silva', 'joao.silva@hospital.com', 'senha123', 'CUIDADOR', true, NOW(), NOW()),
('Maria Santos', 'maria.santos@email.com', 'senha123', 'FAMILIAR', true, NOW(), NOW());

-- Contatos de emergência
INSERT INTO contatos_emergencia (nome, telefone, email, instagram) VALUES 
('Maria Silva', '(11) 98765-4321', 'maria@email.com', '@maria_silva'),
('João Santos', '(11) 95555-1234', 'joao@email.com', '@joao_santos'),
('Ana Costa', '(11) 91111-2222', 'ana@email.com', '@ana_costa');

-- Informações médicas
INSERT INTO informacoes_medicas (tipo_sangue, deficiencia, problemas_especificos) VALUES 
('O+', 'Nenhuma', 'Diabetes, Hipertensão'),
('A+', 'Visual leve', 'Nenhum'),
('B-', 'Nenhuma', 'Asma, Colesterol Alto');

-- Sinais vitais
INSERT INTO sinais_vitais (oxigenio, status_oxigenio, temperatura, status_temperatura, batimentos, status_batimentos) VALUES 
(98.5, 'stable', 36.8, 'stable', 75, 'stable'),
(95.2, 'warning', 37.2, 'warning', 90, 'warning'),
(99.0, 'stable', 36.5, 'stable', 68, 'stable');

-- Pacientes
INSERT INTO pacientes (nome, data_nascimento, genero, relacionamento, telefone, image_url, contato_emergencial_id, informacao_medica_id, sinais_vitais_id) VALUES 
('Carlos Eduardo Silva', '1985-03-15', 'Homem', 'Casado(a)', '(11) 99999-8888', '/assets/carlos-eduardo.jpg', 1, 1, 1),
('Márcia dos Santos', '1992-07-22', 'Mulher', 'Solteiro(a)', '(11) 88888-7777', '/assets/marcia-dos-santos.png', 2, 2, 2),
('Rute da Silva', '1978-11-30', 'Mulher', 'Viúvo(a)', '(11) 77777-6666', '/assets/rute-da-silva.jpg', 3, 3, 3);

-- Alertas de exemplo
INSERT INTO alertas (paciente_id, tipo, mensagem, prioridade, data_criacao, lido, ativo) VALUES 
(2, 'OXIGENIO_BAIXO', 'Oxigenação baixa detectada: 95.2%. Paciente: Márcia dos Santos', 'ALTA', NOW(), false, true),
(2, 'TEMPERATURA_ALTA', 'Temperatura elevada detectada: 37.2°C. Paciente: Márcia dos Santos', 'MEDIA', NOW(), false, true),
(1, 'SISTEMA', 'Paciente cadastrado no sistema com sucesso.', 'BAIXA', NOW(), true, true);