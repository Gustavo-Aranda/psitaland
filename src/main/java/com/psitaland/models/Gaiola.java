package com.psitaland.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "GAIOLAS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Gaiola {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gai_id")
    private Integer id;

    @Column(name = "gai_numero", nullable = false, length = 10)
    private String numero;
}