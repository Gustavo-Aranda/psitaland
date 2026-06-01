package com.psitaland.dtos.passaro;

import com.psitaland.dtos.especie.EspecieResponseDTO;
import com.psitaland.dtos.gaiola.GaiolaResponseDTO;
import com.psitaland.dtos.mutacao.MutacaoResponseDTO;
import com.psitaland.dtos.status.StatusResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) de SAÍDA para Passaro.
 *
 * Padrão DTO: representa os dados que a API devolve ao cliente.
 *
 * Diferente do RequestDTO (que recebe apenas IDs nos relacionamentos),
 * o ResponseDTO devolve os objetos completos de cada relacionamento.
 *
 * Isso permite que o cliente exiba informações como o NOME da espécie
 * e o NÚMERO da gaiola sem precisar fazer chamadas adicionais à API.
 *
 * Exemplo de resposta JSON:
 * {
 *   "id": 1,
 *   "anilha": "A1234",
 *   "dataNascimento": "2023-06-15",
 *   "sexo": "M",
 *   "notaFiscal": "NF-001",
 *   "especie": { "id": 1, "nome": "Calopsita" },
 *   "mutacao": { "id": 2, "descricao": "Lutino" },
 *   "gaiola": { "id": 3, "numero": "G001" },
 *   "status": { "id": 1, "situacao": "Ativo" }
 * }
 */
@Getter
@AllArgsConstructor
public class PassaroResponseDTO {

    private Integer id;
    private String anilha;
    private LocalDate dataNascimento;
    private String sexo;
    private String notaFiscal;

    // Relacionamentos devolvidos como objetos completos (não apenas IDs)
    private EspecieResponseDTO especie;
    private MutacaoResponseDTO mutacao;
    private GaiolaResponseDTO gaiola;
    private StatusResponseDTO status;

}
