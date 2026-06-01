package com.psitaland.services;

import com.psitaland.dto.dashboard.DashboardResponseDTO;
import com.psitaland.dto.dashboard.DashboardResponseDTO.ItemContagem;
import com.psitaland.repositories.EspecieRepository;
import com.psitaland.repositories.GaiolaRepository;
import com.psitaland.repositories.MutacaoRepository;
import com.psitaland.repositories.PassaroRepository;
import com.psitaland.repositories.StatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Serviço responsável por consolidar os indicadores do plantel.
 *
 * Agrega dados de múltiplos repositórios em uma única resposta,
 * oferecendo visão sistêmica do criadouro — o principal problema
 * que o sistema veio resolver em relação às planilhas.
 *
 * Usa o padrão Builder do Lombok para construir o DTO de forma
 * legível, campo a campo, sem construtores de 10 parâmetros.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardService {

    private final PassaroRepository  passaroRepository;
    private final EspecieRepository  especieRepository;
    private final GaiolaRepository   gaiolaRepository;
    private final MutacaoRepository  mutacaoRepository;
    private final StatusRepository   statusRepository;

    @Transactional(readOnly = true)
    public DashboardResponseDTO gerarDashboard() {
        log.info("Gerando dados do dashboard do plantel.");

        DashboardResponseDTO dashboard = DashboardResponseDTO.builder()
                .totalAves(contarAves())
                .totalEspecies(especieRepository.count())
                .totalGaiolas(gaiolaRepository.count())
                .totalMutacoes(mutacaoRepository.count())
                .avesPorStatus(contarAvesPorStatus())
                .avesPorEspecie(contarAvesPorEspecie())
                .avesPorGaiola(contarAvesPorGaiola())
                .build();

        log.info("Dashboard gerado: {} aves no plantel.", dashboard.getTotalAves());
        return dashboard;
    }

    // ----------------------------------------------------------------
    // Métodos privados de contagem
    // ----------------------------------------------------------------

    private long contarAves() {
        return passaroRepository.count();
    }

    /**
     * Para cada status cadastrado, conta quantas aves possuem aquele status.
     * Retorna apenas os status que possuem ao menos uma ave.
     */
    private List<ItemContagem> contarAvesPorStatus() {
        return statusRepository.findAll()
                .stream()
                .map(status -> ItemContagem.builder()
                        .label(status.getSituacao())
                        .total(passaroRepository.findByStatusId(status.getId()).size())
                        .build())
                .filter(item -> item.getTotal() > 0)
                .toList();
    }

    /**
     * Para cada espécie cadastrada, conta quantas aves pertencem a ela.
     * Retorna apenas as espécies que possuem ao menos uma ave.
     */
    private List<ItemContagem> contarAvesPorEspecie() {
        return especieRepository.findAll()
                .stream()
                .map(especie -> ItemContagem.builder()
                        .label(especie.getNome())
                        .total(passaroRepository.findByEspecieId(especie.getId()).size())
                        .build())
                .filter(item -> item.getTotal() > 0)
                .toList();
    }

    /**
     * Para cada gaiola cadastrada, conta quantas aves estão nela.
     * Retorna apenas as gaiolas que possuem ao menos uma ave.
     */
    private List<ItemContagem> contarAvesPorGaiola() {
        return gaiolaRepository.findAll()
                .stream()
                .map(gaiola -> ItemContagem.builder()
                        .label(gaiola.getNumero())
                        .total(passaroRepository.findByGaiolaId(gaiola.getId()).size())
                        .build())
                .filter(item -> item.getTotal() > 0)
                .toList();
    }

}
