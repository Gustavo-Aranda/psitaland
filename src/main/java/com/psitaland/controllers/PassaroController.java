package com.psitaland.controllers;

import com.psitaland.dtos.passaro.PassaroRequestDTO;
import com.psitaland.dtos.passaro.PassaroResponseDTO;
import com.psitaland.services.PassaroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável pelos endpoints de Passaro.
 *
 * É o controller mais completo do sistema, pois além do CRUD padrão
 * expõe endpoints de consulta específicos do negócio e a movimentação.
 *
 * Arquitetura em camadas:
 * - Recebe requisições HTTP
 * - Delega toda lógica ao PassaroService
 * - Devolve respostas HTTP com status adequados
 *
 * O Controller não contém nenhuma regra de negócio.
 */
@Tag(name = "Pássaros", description = "Gerenciamento do plantel de aves")
@RestController
@RequestMapping("/passaros")
@RequiredArgsConstructor
public class PassaroController {

    private final PassaroService passaroService;

    // ----------------------------------------------------------------
    // CRUD básico
    // ----------------------------------------------------------------

    @Operation(summary = "Listar todos os pássaros do plantel")
    @GetMapping
    public ResponseEntity<List<PassaroResponseDTO>> listarTodos() {
        return ResponseEntity.ok(passaroService.listarTodos());
    }

    @Operation(summary = "Buscar pássaro por ID")
    @GetMapping("/{id}")
    public ResponseEntity<PassaroResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(passaroService.buscarPorId(id));
    }

    @Operation(summary = "Buscar pássaro pela anilha")
    @GetMapping("/anilha/{anilha}")
    public ResponseEntity<PassaroResponseDTO> buscarPorAnilha(@PathVariable String anilha) {
        return ResponseEntity.ok(passaroService.buscarPorAnilha(anilha));
    }

    @Operation(summary = "Cadastrar novo pássaro no plantel")
    @PostMapping
    public ResponseEntity<PassaroResponseDTO> criar(@Valid @RequestBody PassaroRequestDTO dto) {
        PassaroResponseDTO criado = passaroService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @Operation(summary = "Atualizar dados de um pássaro")
    @PutMapping("/{id}")
    public ResponseEntity<PassaroResponseDTO> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody PassaroRequestDTO dto) {
        return ResponseEntity.ok(passaroService.atualizar(id, dto));
    }

    @Operation(summary = "Excluir pássaro do plantel")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        passaroService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    // ----------------------------------------------------------------
    // Movimentação
    // ----------------------------------------------------------------

    @Operation(summary = "Mover pássaro para outra gaiola")
    @PutMapping("/{id}/mover/{gaiolaId}")
    public ResponseEntity<PassaroResponseDTO> mover(
            @PathVariable Integer id,
            @PathVariable Integer gaiolaId) {
        return ResponseEntity.ok(passaroService.mover(id, gaiolaId));
    }

    // ----------------------------------------------------------------
    // Consultas de negócio
    // ----------------------------------------------------------------

    @Operation(summary = "Listar pássaros por espécie")
    @GetMapping("/especie/{especieId}")
    public ResponseEntity<List<PassaroResponseDTO>> listarPorEspecie(@PathVariable Integer especieId) {
        return ResponseEntity.ok(passaroService.listarPorEspecie(especieId));
    }

    @Operation(summary = "Listar pássaros por gaiola")
    @GetMapping("/gaiola/{gaiolaId}")
    public ResponseEntity<List<PassaroResponseDTO>> listarPorGaiola(@PathVariable Integer gaiolaId) {
        return ResponseEntity.ok(passaroService.listarPorGaiola(gaiolaId));
    }

    @Operation(summary = "Listar pássaros por status")
    @GetMapping("/status/{statusId}")
    public ResponseEntity<List<PassaroResponseDTO>> listarPorStatus(@PathVariable Integer statusId) {
        return ResponseEntity.ok(passaroService.listarPorStatus(statusId));
    }

    @Operation(summary = "Listar pássaros por mutação")
    @GetMapping("/mutacao/{mutacaoId}")
    public ResponseEntity<List<PassaroResponseDTO>> listarPorMutacao(@PathVariable Integer mutacaoId) {
        return ResponseEntity.ok(passaroService.listarPorMutacao(mutacaoId));
    }

}
