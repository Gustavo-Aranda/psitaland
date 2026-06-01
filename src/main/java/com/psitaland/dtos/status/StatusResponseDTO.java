package com.psitaland.dtos.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO (Data Transfer Object) de SAÍDA para Status.
 *
 * Padrão DTO: representa os dados que a API devolve ao cliente.
 */
@Getter
@AllArgsConstructor
public class StatusResponseDTO {

    private Integer id;
    private String situacao;

}
