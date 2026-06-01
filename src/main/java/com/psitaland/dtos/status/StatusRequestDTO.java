package com.psitaland.dtos.status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO (Data Transfer Object) de ENTRADA para Status.
 *
 * Padrão DTO: representa os dados que o CLIENTE envia para a API.
 * A entidade Status nunca é exposta diretamente ao cliente.
 */
@Getter
@Setter
public class StatusRequestDTO {

    /**
     * Situação do status. Ex: "Ativo", "Vendido", "Óbito", "Transferido", "Reservado".
     * Não pode ser vazia e tem limite de 50 caracteres.
     */
    @NotBlank(message = "A situação do status é obrigatória.")
    @Size(max = 50, message = "A situação do status deve ter no máximo 50 caracteres.")
    private String situacao;

}
