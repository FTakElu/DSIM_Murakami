package teste.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import teste.model.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    
    // Métodos personalizados podem ser adicionados aqui
    // O Spring Data JPA já fornece os métodos básicos:
    // save(), findById(), findAll(), deleteById(), etc.
}