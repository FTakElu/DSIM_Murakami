package teste.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "sinais_vitais")
public class SinaisVitais implements Serializable {

    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Double oxigenio;
    
    @Column(name = "status_oxigenio")
    private String statusOxigenio;
    
    private Double temperatura;
    
    @Column(name = "status_temperatura")
    private String statusTemperatura;
    
    private Integer batimentos;
    
    @Column(name = "status_batimentos")
    private String statusBatimentos;
}