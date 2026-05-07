package com.psitaland.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "STATUS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stt_id")
    private Integer id;

    @Column(name = "stt_situacao", nullable = false, length = 100)
    private String situacao;
}