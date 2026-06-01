package com.psitaland.controllers;

import com.psitaland.dtos.mutacao.MutacaoRequestDTO;
import com.psitaland.dtos.mutacao.MutacaoResponseDTO;
import com.psitaland.services.MutacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável pelos endpoints de Mutacao.
 *
 * Arquitetura em camadas:
 * - Recebe requisições HTTP
 * - Delega toda lógica ao MutacaoService
 * - Devolve respostas HTTP com status adequados
 */
@Tag(name = "Mutações", description = "Gerenciamento de mutações das aves")
@RestController
@RequestMapping("/mutacoes")
@RequiredArgsConstructor
public class MutacaoController {

    private final MutacaoService mutacaoService;

    @Operation(summary = "Listar todas as mutações")
    @GetMapping
    public ResponseEntity<List<MutacaoResponseDTO>> listarTodas() {
        return ResponseEntity.ok(mutacaoService.listarTodas());
    }

    @Operation(summary = "Buscar mutação por ID")
    @GetMapping("/{id}")
    public ResponseEntity<MutacaoResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(mutacaoService.buscarPorId(id));
    }

    @Operation(summary = "Cadastrar nova mutação")
    @PostMapping
    public ResponseEntity<MutacaoResponseDTO> criar(@Valid @RequestBody MutacaoRequestDTO dto) {
        MutacaoResponseDTO criada = mutacaoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    @Operation(summary = "Atualizar mutação existente")
    @PutMapping("/{id}")
    public ResponseEntity<MutacaoResponseDTO> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody MutacaoRequestDTO dto) {
        return ResponseEntity.ok(mutacaoService.atualizar(id, dto));
    }

    @Operation(summary = "Excluir mutação")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        mutacaoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
