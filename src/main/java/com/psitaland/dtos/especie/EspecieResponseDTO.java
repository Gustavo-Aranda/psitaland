package com.psitaland.dtos.especie;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO (Data Transfer Object) de SAÍDA para Especie.
 *
 * Padrão DTO: representa os dados que a API devolve ao cliente.
 * Contém o id (necessário para o cliente referenciar o recurso)
 * e o nome da espécie.
 *
 * Imutável por design: só possui getter, sem setter.
 * O objeto é construído uma vez no Service e não deve ser alterado.
 */
@Getter
@AllArgsConstructor
public class EspecieResponseDTO {

    private Integer id;
    private String nome;

}
