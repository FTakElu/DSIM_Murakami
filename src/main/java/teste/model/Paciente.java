package teste.model;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "pacientes")
public class Paciente implements Serializable {

    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nome;
    
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;
    
    @Column(length = 50)
    private String genero;
    
    @Column(length = 100)
    private String relacionamento;
    
    @Column(length = 20)
    private String telefone;
    
    @Column(name = "image_url", length = 500)
    private String imageUrl;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contato_emergencial_id")
    private ContatoEmergencial contatoEmergencial;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "informacao_medica_id")
    private InformacaoMedica informacaoMedica;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sinais_vitais_id")
    private SinaisVitais sinaisVitais;
}