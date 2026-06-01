package com.psitaland.controllers;

import com.psitaland.dtos.status.StatusRequestDTO;
import com.psitaland.dtos.status.StatusResponseDTO;
import com.psitaland.services.StatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável pelos endpoints de Status.
 *
 * Arquitetura em camadas:
 * - Recebe requisições HTTP
 * - Delega toda lógica ao StatusService
 * - Devolve respostas HTTP com status adequados
 */
@Tag(name = "Status", description = "Gerenciamento de status das aves")
@RestController
@RequestMapping("/status")
@RequiredArgsConstructor
public class StatusController {

    private final StatusService statusService;

    @Operation(summary = "Listar todos os status")
    @GetMapping
    public ResponseEntity<List<StatusResponseDTO>> listarTodos() {
        return ResponseEntity.ok(statusService.listarTodos());
    }

    @Operation(summary = "Buscar status por ID")
    @GetMapping("/{id}")
    public ResponseEntity<StatusResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(statusService.buscarPorId(id));
    }

    @Operation(summary = "Cadastrar novo status")
    @PostMapping
    public ResponseEntity<StatusResponseDTO> criar(@Valid @RequestBody StatusRequestDTO dto) {
        StatusResponseDTO criado = statusService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @Operation(summary = "Atualizar status existente")
    @PutMapping("/{id}")
    public ResponseEntity<StatusResponseDTO> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody StatusRequestDTO dto) {
        return ResponseEntity.ok(statusService.atualizar(id, dto));
    }

    @Operation(summary = "Excluir status")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        statusService.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
