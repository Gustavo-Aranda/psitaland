package com.psitaland.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ESPECIES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Especie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "esp_id")
    private Integer id;

    @Column(name = "esp_nome", nullable = false, length = 100)
    private String nome;
}