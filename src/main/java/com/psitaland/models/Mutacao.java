package com.psitaland.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "MUTACOES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mutacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mut_id")
    private Integer id;

    @Column(name = "mut_descricao", nullable = false, length = 100)
    private String descricao;
}