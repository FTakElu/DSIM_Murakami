-- Script de inicialização PostgreSQL - DADOS LIMPOS E ESSENCIAIS
-- Este script cria apenas os dados mínimos necessários para o sistema

-- Inserir usuário administrador padrão (ÚNICO USUÁRIO INICIAL)
INSERT INTO usuario (id, nome, email, senha, tipo, ativo, data_criacao, data_atualizacao) VALUES 
(1, 'Administrador Sistema', 'admin@dsim.com', '$2a$10$N.zmdr9k7uOCQb96VdlmVe7tq4hKg3TX.Nqc6cKGFUl4bGfMo', 'ADMIN', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Inserir alguns pacientes de exemplo (DADOS LIMPOS)
INSERT INTO paciente (id, nome, data_nascimento, genero, telefone, email, endereco, cidade, estado, cep, tipo_sanguineo, alergias, condicoes_medicas, medicamentos_atuais, quarto, ativo, data_criacao, data_atualizacao) VALUES 
(1, 'João Silva', '1985-03-15', 'Masculino', '(11) 99999-0001', 'joao@email.com', 'Rua A, 123', 'São Paulo', 'SP', '01234-567', 'O+', 'Nenhuma', 'Hipertensão', 'Losartana 50mg', '101', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Maria Santos', '1990-07-22', 'Feminino', '(11) 99999-0002', 'maria@email.com', 'Rua B, 456', 'São Paulo', 'SP', '01234-890', 'A+', 'Penicilina', 'Diabetes Tipo 2', 'Metformina 850mg', '102', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Inserir contatos de emergência
INSERT INTO contato_emergencial (id, nome, parentesco, telefone, email, paciente_id) VALUES 
(1, 'Ana Silva', 'Esposa', '(11) 88888-0001', 'ana@email.com', 1),
(2, 'Pedro Santos', 'Marido', '(11) 88888-0002', 'pedro@email.com', 2);

-- Inserir sinais vitais iniciais
INSERT INTO sinais_vitais (id, oxigenio, temperatura, batimentos, pressao_sistolica, pressao_diastolica, frequencia_respiratoria, status_oxigenio, status_temperatura, status_batimentos, status_pressao, data_medicao, paciente_id) VALUES 
(1, 98.5, 36.8, 75, 120, 80, 16, 'NORMAL', 'NORMAL', 'NORMAL', 'NORMAL', CURRENT_TIMESTAMP, 1),
(2, 97.2, 37.1, 85, 130, 85, 18, 'NORMAL', 'ELEVADA', 'ELEVADO', 'ATENCAO', CURRENT_TIMESTAMP, 2);

-- Reset sequences para PostgreSQL
SELECT setval('usuario_id_seq', (SELECT MAX(id) FROM usuario));
SELECT setval('paciente_id_seq', (SELECT MAX(id) FROM paciente));
SELECT setval('contato_emergencial_id_seq', (SELECT MAX(id) FROM contato_emergencial));
SELECT setval('sinais_vitais_id_seq', (SELECT MAX(id) FROM sinais_vitais));