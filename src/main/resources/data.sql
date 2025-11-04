-- Dados iniciais para teste

-- Usuários do sistema (senhas criptografadas com BCrypt - senha original: senha123)
INSERT INTO usuarios (nome, email, senha, ativo, data_criacao, data_atualizacao) VALUES 
('Administrador Sistema', 'admin@sistema.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);