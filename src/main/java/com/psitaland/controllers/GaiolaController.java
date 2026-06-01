package com.psitaland.controllers;

import com.psitaland.dtos.gaiola.GaiolaRequestDTO;
import com.psitaland.dtos.gaiola.GaiolaResponseDTO;
import com.psitaland.services.GaiolaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável pelos endpoints de Gaiola.
 *
 * Arquitetura em camadas:
 * - Recebe requisições HTTP
 * - Delega toda lógica ao GaiolaService
 * - Devolve respostas HTTP com status adequados
 */
@Tag(name = "Gaiolas", description = "Gerenciamento de gaiolas (localizações físicas do criadouro)")
@RestController
@RequestMapping("/gaiolas")
@RequiredArgsConstructor
public class GaiolaController {

    private final GaiolaService gaiolaService;

    @Operation(summary = "Listar todas as gaiolas")
    @GetMapping
    public ResponseEntity<List<GaiolaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(gaiolaService.listarTodas());
    }

    @Operation(summary = "Buscar gaiola por ID")
    @GetMapping("/{id}")
    public ResponseEntity<GaiolaResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(gaiolaService.buscarPorId(id));
    }

    @Operation(summary = "Cadastrar nova gaiola")
    @PostMapping
    public ResponseEntity<GaiolaResponseDTO> criar(@Valid @RequestBody GaiolaRequestDTO dto) {
        GaiolaResponseDTO criada = gaiolaService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    @Operation(summary = "Atualizar gaiola existente")
    @PutMapping("/{id}")
    public ResponseEntity<GaiolaResponseDTO> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody GaiolaRequestDTO dto) {
        return ResponseEntity.ok(gaiolaService.atualizar(id, dto));
    }

    @Operation(summary = "Excluir gaiola")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        gaiolaService.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
