package com.psitaland.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "PASSAROS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Passaro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pas_id")
    private Integer id;

    @Column(name = "pas_anilha", nullable = false, length = 100)
    private String anilha;

    @Column(name = "pas_data_nasc", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "pas_sexo", nullable = false, length = 1)
    private String sexo; // M ou F

    @Column(name = "pas_not_fis", nullable = false, length = 100)
    private String notaFiscal;

    // Relacionamentos
    @ManyToOne
    @JoinColumn(name = "pas_esp_id", nullable = false)
    private Especie especie;

    @ManyToOne
    @JoinColumn(name = "pas_mut_id", nullable = false)
    private Mutacao mutacao;

    @ManyToOne
    @JoinColumn(name = "pas_gai_id", nullable = false)
    private Gaiola gaiola;

    @ManyToOne
    @JoinColumn(name = "pas_stt_id", nullable = false)
    private Status status;
}