package com.psitaland.dtos.mutacao;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO (Data Transfer Object) de SAÍDA para Mutacao.
 *
 * Padrão DTO: representa os dados que a API devolve ao cliente.
 */
@Getter
@AllArgsConstructor
public class MutacaoResponseDTO {

    private Integer id;
    private String descricao;

}
