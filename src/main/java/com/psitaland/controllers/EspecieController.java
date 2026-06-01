package com.psitaland.controllers;

import com.psitaland.dtos.especie.EspecieRequestDTO;
import com.psitaland.dtos.especie.EspecieResponseDTO;
import com.psitaland.services.EspecieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável pelos endpoints de Especie.
 *
 * Arquitetura em camadas:
 * - Recebe requisições HTTP
 * - Delega toda lógica ao EspecieService
 * - Devolve respostas HTTP com status adequados
 *
 * O Controller não contém regras de negócio.
 * Seu único papel é ser a porta de entrada da API.
 */
@Tag(name = "Espécies", description = "Gerenciamento de espécies de aves")
@RestController
@RequestMapping("/especies")
@RequiredArgsConstructor
public class EspecieController {

    private final EspecieService especieService;

    @Operation(summary = "Listar todas as espécies")
    @GetMapping
    public ResponseEntity<List<EspecieResponseDTO>> listarTodas() {
        return ResponseEntity.ok(especieService.listarTodas());
    }

    @Operation(summary = "Buscar espécie por ID")
    @GetMapping("/{id}")
    public ResponseEntity<EspecieResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(especieService.buscarPorId(id));
    }

    @Operation(summary = "Cadastrar nova espécie")
    @PostMapping
    public ResponseEntity<EspecieResponseDTO> criar(@Valid @RequestBody EspecieRequestDTO dto) {
        EspecieResponseDTO criada = especieService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    @Operation(summary = "Atualizar espécie existente")
    @PutMapping("/{id}")
    public ResponseEntity<EspecieResponseDTO> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody EspecieRequestDTO dto) {
        return ResponseEntity.ok(especieService.atualizar(id, dto));
    }

    @Operation(summary = "Excluir espécie")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        especieService.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
