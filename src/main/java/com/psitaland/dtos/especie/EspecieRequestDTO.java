package com.psitaland.dtos.especie;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO (Data Transfer Object) de ENTRADA para espécie.
 *
 * Padrão DTO: representa os dados que o CLIENTE envia para a API.
 * A entidade Especie nunca é exposta diretamente ao cliente.
 *
 * Validações Bean Validation garantem que dados inválidos
 * são rejeitados antes de chegar na camada de serviço.
 */
@Getter
@Setter
public class EspecieRequestDTO {

    /**
     * Nome da espécie. Ex: "Calopsita", "Ring Neck", "Agapornis".
     * Não pode ser vazio e tem limite de 100 caracteres.
     */
    @NotBlank(message = "O nome da espécie é obrigatório.")
    @Size(max = 100, message = "O nome da espécie deve ter no máximo 100 caracteres.")
    private String nome;

}
