package com.psitaland.dtos.passaro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) de ENTRADA para Passaro.
 *
 * Padrão DTO: representa os dados que o CLIENTE envia para a API.
 * A entidade Passaro nunca é exposta diretamente ao cliente.
 *
 * Para os relacionamentos (especie, mutacao, gaiola, status),
 * o cliente envia apenas o ID — não o objeto inteiro.
 * Isso evita payloads gigantes e protege a integridade dos dados.
 *
 * Exemplo de payload JSON esperado:
 * {
 *   "anilha": "A1234",
 *   "dataNascimento": "2023-06-15",
 *   "sexo": "M",
 *   "notaFiscal": "NF-001",
 *   "especieId": 1,
 *   "mutacaoId": 2,
 *   "gaiolaId": 3,
 *   "statusId": 1
 * }
 */
@Getter
@Setter
public class PassaroRequestDTO {

    /**
     * Anilha é o identificador físico único da ave (anel na pata).
     * Funciona como a "identidade" do pássaro no plantel.
     */
    @NotBlank(message = "A anilha é obrigatória.")
    @Size(max = 20, message = "A anilha deve ter no máximo 20 caracteres.")
    private String anilha;

    /**
     * Data de nascimento da ave.
     * Deve ser uma data no passado — não faz sentido cadastrar
     * um pássaro que ainda não nasceu.
     */
    @NotNull(message = "A data de nascimento é obrigatória.")
    @Past(message = "A data de nascimento deve ser uma data no passado.")
    private LocalDate dataNascimento;

    /**
     * Sexo da ave. Valores esperados: "M" (macho) ou "F" (fêmea).
     */
    @NotBlank(message = "O sexo é obrigatório.")
    @Size(max = 1, message = "O sexo deve ser 'M' ou 'F'.")
    private String sexo;

    /**
     * Número da nota fiscal de aquisição da ave.
     * Campo opcional — pode ser nulo para aves nascidas no próprio criadouro.
     */
    @Size(max = 50, message = "A nota fiscal deve ter no máximo 50 caracteres.")
    private String notaFiscal;

    /**
     * ID da espécie da ave. Ex: 1 = Calopsita, 2 = Ring Neck.
     * O cliente envia apenas o ID — não o objeto Especie completo.
     */
    @NotNull(message = "A espécie é obrigatória.")
    private Integer especieId;

    /**
     * ID da mutação da ave. Ex: 1 = Lutino, 2 = Albino.
     */
    @NotNull(message = "A mutação é obrigatória.")
    private Integer mutacaoId;

    /**
     * ID da gaiola onde a ave está localizada. Ex: 1 = G001.
     */
    @NotNull(message = "A gaiola é obrigatória.")
    private Integer gaiolaId;

    /**
     * ID do status atual da ave. Ex: 1 = Ativo, 2 = Vendido.
     */
    @NotNull(message = "O status é obrigatório.")
    private Integer statusId;

}
