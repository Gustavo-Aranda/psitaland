package com.psitaland.services;

import com.psitaland.dtos.gaiola.GaiolaRequestDTO;
import com.psitaland.dtos.gaiola.GaiolaResponseDTO;
import com.psitaland.exception.BusinessRuleException;
import com.psitaland.exception.ResourceNotFoundException;
import com.psitaland.models.Gaiola;
import com.psitaland.repositories.GaiolaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Serviço responsável pela lógica de negócio de Gaiola.
 *
 * Arquitetura em camadas:
 * - Recebe DTOs do Controller
 * - Aplica regras de negócio
 * - Persiste via Repository
 * - Devolve DTOs ao Controller
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GaiolaService {

    private final GaiolaRepository gaiolaRepository;

    // ----------------------------------------------------------------
    // Listar todas
    // ----------------------------------------------------------------

    @Transactional(readOnly = true)
    public List<GaiolaResponseDTO> listarTodas() {
        log.info("Listando todas as gaiolas.");
        return gaiolaRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // ----------------------------------------------------------------
    // Buscar por ID
    // ----------------------------------------------------------------

    @Transactional(readOnly = true)
    public GaiolaResponseDTO buscarPorId(Integer id) {
        log.info("Buscando gaiola com id {}.", id);
        Gaiola gaiola = buscarEntidadePorId(id);
        return toResponseDTO(gaiola);
    }

    // ----------------------------------------------------------------
    // Criar
    // ----------------------------------------------------------------

    @Transactional
    public GaiolaResponseDTO criar(GaiolaRequestDTO dto) {
        log.info("Criando gaiola com número '{}'.", dto.getNumero());

        validarNumeroDuplicado(dto.getNumero(), null);

        Gaiola gaiola = new Gaiola();
        gaiola.setNumero(dto.getNumero());

        Gaiola salva = gaiolaRepository.save(gaiola);
        log.info("Gaiola criada com id {}.", salva.getId());

        return toResponseDTO(salva);
    }

    // ----------------------------------------------------------------
    // Atualizar
    // ----------------------------------------------------------------

    @Transactional
    public GaiolaResponseDTO atualizar(Integer id, GaiolaRequestDTO dto) {
        log.info("Atualizando gaiola com id {}.", id);

        Gaiola gaiola = buscarEntidadePorId(id);
        validarNumeroDuplicado(dto.getNumero(), id);

        gaiola.setNumero(dto.getNumero());

        Gaiola atualizada = gaiolaRepository.save(gaiola);
        log.info("Gaiola com id {} atualizada.", id);

        return toResponseDTO(atualizada);
    }

    // ----------------------------------------------------------------
    // Excluir
    // ----------------------------------------------------------------

    @Transactional
    public void excluir(Integer id) {
        log.info("Excluindo gaiola com id {}.", id);
        Gaiola gaiola = buscarEntidadePorId(id);
        gaiolaRepository.delete(gaiola);
        log.info("Gaiola com id {} excluída.", id);
    }

    // ----------------------------------------------------------------
    // Métodos auxiliares
    // ----------------------------------------------------------------

    public Gaiola buscarEntidadePorId(Integer id) {
        return gaiolaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gaiola", id));
    }

    private void validarNumeroDuplicado(String numero, Integer idAtual) {
        gaiolaRepository.findByNumeroIgnoreCase(numero).ifPresent(existente -> {
            if (!existente.getId().equals(idAtual)) {
                throw new BusinessRuleException("Já existe uma gaiola cadastrada com o número '" + numero + "'.");
            }
        });
    }

    private GaiolaResponseDTO toResponseDTO(Gaiola gaiola) {
        return new GaiolaResponseDTO(gaiola.getId(), gaiola.getNumero());
    }

}
