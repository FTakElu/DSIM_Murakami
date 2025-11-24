package teste.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import teste.model.enums.NivelPrioridade;
import teste.model.enums.TipoAlerta;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "alertas")
public class Alerta implements Serializable {

    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAlerta tipo;
    
    @Column(nullable = false, length = 500)
    private String mensagem;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NivelPrioridade prioridade;
    
    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;
    
    @Column(name = "data_leitura")
    private LocalDateTime dataLeitura;
    
    @Column(name = "lido")
    private Boolean lido = false;
    
    @Column(name = "ativo")
    private Boolean ativo = true;
    
    // Limites configuráveis para os alertas
    @Column(name = "limite_oxigenio_min")
    private Double limiteOxigenioMin = 95.0;
    
    @Column(name = "limite_oxigenio_max")
    private Double limiteOxigenioMax = 100.0;
    
    @Column(name = "limite_temperatura_min")
    private Double limiteTemperaturaMin = 36.0;
    
    @Column(name = "limite_temperatura_max")
    private Double limiteTemperaturaMax = 37.5;
    
    @Column(name = "limite_batimentos_min")
    private Integer limiteBatimentosMin = 60;
    
    @Column(name = "limite_batimentos_max")
    private Integer limiteBatimentosMax = 100;
    
    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }
}