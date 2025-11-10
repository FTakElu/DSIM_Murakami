package teste.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import teste.model.SinaisVitais;

@Repository
public interface SinaisVitaisRepository extends JpaRepository<SinaisVitais, Long> {
}