package com.psitaland.dtos.mutacao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO (Data Transfer Object) de ENTRADA para Mutacao.
 *
 * Padrão DTO: representa os dados que o CLIENTE envia para a API.
 * A entidade Mutacao nunca é exposta diretamente ao cliente.
 */
@Getter
@Setter
public class MutacaoRequestDTO {

    /**
     * Descrição da mutação. Ex: "Lutino", "Albino", "Cara Branca".
     * Não pode ser vazia e tem limite de 100 caracteres.
     */
    @NotBlank(message = "A descrição da mutação é obrigatória.")
    @Size(max = 100, message = "A descrição da mutação deve ter no máximo 100 caracteres.")
    private String descricao;

}
