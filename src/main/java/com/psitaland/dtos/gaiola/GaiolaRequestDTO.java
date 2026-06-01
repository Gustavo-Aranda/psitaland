package com.psitaland.dtos.gaiola;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO (Data Transfer Object) de ENTRADA para Gaiola.
 *
 * Padrão DTO: representa os dados que o CLIENTE envia para a API.
 * A entidade Gaiola nunca é exposta diretamente ao cliente.
 */
@Getter
@Setter
public class GaiolaRequestDTO {

    /**
     * Número/identificação da gaiola. Ex: "G001", "G002".
     * Representa uma localização física dentro do criadouro.
     * Não pode ser vazio e tem limite de 20 caracteres.
     */
    @NotBlank(message = "O número da gaiola é obrigatório.")
    @Size(max = 20, message = "O número da gaiola deve ter no máximo 20 caracteres.")
    private String numero;

}
