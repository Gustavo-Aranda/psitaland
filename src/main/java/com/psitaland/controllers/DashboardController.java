package com.psitaland.controllers;

import com.psitaland.dtos.dashboard.DashboardResponseDTO;
import com.psitaland.services.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsável pelo endpoint de Dashboard.
 *
 * Expõe um único endpoint GET que consolida todos os indicadores
 * do plantel em uma única chamada à API.
 *
 * Arquitetura em camadas:
 * - Recebe requisição HTTP
 * - Delega ao DashboardService
 * - Devolve resposta com status 200
 */
@Tag(name = "Dashboard", description = "Indicadores e visão geral do plantel")
@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @Operation(
        summary = "Visão geral do plantel",
        description = "Retorna indicadores consolidados: total de aves, espécies, " +
                      "gaiolas, mutações e distribuições por status, espécie e gaiola."
    )
    @GetMapping
    public ResponseEntity<DashboardResponseDTO> getDashboard() {
        return ResponseEntity.ok(dashboardService.gerarDashboard());
    }

}
