package com.psitaland.dto.dashboard;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * DTO de saída do Dashboard.
 *
 * Agrega em uma única resposta todos os indicadores do plantel,
 * evitando que o cliente precise fazer múltiplas chamadas à API.
 *
 * Usa o padrão Builder (Lombok @Builder) para construção fluente —
 * ideal para objetos com muitos campos opcionais.
 *
 * Exemplo de resposta JSON:
 * {
 *   "totalAves": 42,
 *   "totalEspecies": 4,
 *   "totalGaiolas": 10,
 *   "totalMutacoes": 6,
 *   "avesPorStatus": [
 *     { "label": "Ativo",   "total": 30 },
 *     { "label": "Vendido", "total": 8  },
 *     { "label": "Óbito",   "total": 4  }
 *   ],
 *   "avesPorEspecie": [
 *     { "label": "Calopsita", "total": 20 },
 *     { "label": "Agapornis", "total": 12 }
 *   ],
 *   "avesPorGaiola": [
 *     { "label": "G001", "total": 5 },
 *     { "label": "G002", "total": 3 }
 *   ]
 * }
 */
@Getter
@Builder
public class DashboardResponseDTO {

    /** Total de aves cadastradas no plantel */
    private long totalAves;

    /** Total de espécies cadastradas */
    private long totalEspecies;

    /** Total de gaiolas cadastradas */
    private long totalGaiolas;

    /** Total de mutações cadastradas */
    private long totalMutacoes;

    /** Distribuição de aves por status (ex: Ativo, Vendido, Óbito) */
    private List<ItemContagem> avesPorStatus;

    /** Distribuição de aves por espécie (ex: Calopsita, Agapornis) */
    private List<ItemContagem> avesPorEspecie;

    /** Distribuição de aves por gaiola (ex: G001, G002) */
    private List<ItemContagem> avesPorGaiola;

    // ----------------------------------------------------------------
    // Classe interna: representa um item de contagem genérico
    // ----------------------------------------------------------------

    /**
     * Par label + total usado nos agrupamentos do dashboard.
     *
     * Classe interna estática para manter o DTO coeso —
     * ItemContagem só faz sentido no contexto do Dashboard.
     */
    @Getter
    @Builder
    public static class ItemContagem {

        /** Nome do agrupamento. Ex: "Calopsita", "Ativo", "G001" */
        private String label;

        /** Quantidade de aves neste agrupamento */
        private long total;

    }

}
