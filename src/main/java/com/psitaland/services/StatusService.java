package com.psitaland.services;

import com.psitaland.dtos.status.StatusRequestDTO;
import com.psitaland.dtos.status.StatusResponseDTO;
import com.psitaland.exception.BusinessRuleException;
import com.psitaland.exception.ResourceNotFoundException;
import com.psitaland.models.Status;
import com.psitaland.repositories.StatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Serviço responsável pela lógica de negócio de Status.
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
public class StatusService {

    private final StatusRepository statusRepository;

    // ----------------------------------------------------------------
    // Listar todos
    // ----------------------------------------------------------------

    @Transactional(readOnly = true)
    public List<StatusResponseDTO> listarTodos() {
        log.info("Listando todos os status.");
        return statusRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // ----------------------------------------------------------------
    // Buscar por ID
    // ----------------------------------------------------------------

    @Transactional(readOnly = true)
    public StatusResponseDTO buscarPorId(Integer id) {
        log.info("Buscando status com id {}.", id);
        Status status = buscarEntidadePorId(id);
        return toResponseDTO(status);
    }

    // ----------------------------------------------------------------
    // Criar
    // ----------------------------------------------------------------

    @Transactional
    public StatusResponseDTO criar(StatusRequestDTO dto) {
        log.info("Criando status com situação '{}'.", dto.getSituacao());

        validarSituacaoDuplicada(dto.getSituacao(), null);

        Status status = new Status();
        status.setSituacao(dto.getSituacao());

        Status salvo = statusRepository.save(status);
        log.info("Status criado com id {}.", salvo.getId());

        return toResponseDTO(salvo);
    }

    // ----------------------------------------------------------------
    // Atualizar
    // ----------------------------------------------------------------

    @Transactional
    public StatusResponseDTO atualizar(Integer id, StatusRequestDTO dto) {
        log.info("Atualizando status com id {}.", id);

        Status status = buscarEntidadePorId(id);
        validarSituacaoDuplicada(dto.getSituacao(), id);

        status.setSituacao(dto.getSituacao());

        Status atualizado = statusRepository.save(status);
        log.info("Status com id {} atualizado.", id);

        return toResponseDTO(atualizado);
    }

    // ----------------------------------------------------------------
    // Excluir
    // ----------------------------------------------------------------

    @Transactional
    public void excluir(Integer id) {
        log.info("Excluindo status com id {}.", id);
        Status status = buscarEntidadePorId(id);
        statusRepository.delete(status);
        log.info("Status com id {} excluído.", id);
    }

    // ----------------------------------------------------------------
    // Métodos auxiliares
    // ----------------------------------------------------------------

    public Status buscarEntidadePorId(Integer id) {
        return statusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Status", id));
    }

    private void validarSituacaoDuplicada(String situacao, Integer idAtual) {
        statusRepository.findBySituacaoIgnoreCase(situacao).ifPresent(existente -> {
            if (!existente.getId().equals(idAtual)) {
                throw new BusinessRuleException("Já existe um status cadastrado com a situação '" + situacao + "'.");
            }
        });
    }

    private StatusResponseDTO toResponseDTO(Status status) {
        return new StatusResponseDTO(status.getId(), status.getSituacao());
    }

}
