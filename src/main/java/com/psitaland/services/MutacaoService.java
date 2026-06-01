package com.psitaland.services;

import com.psitaland.dtos.mutacao.MutacaoRequestDTO;
import com.psitaland.dtos.mutacao.MutacaoResponseDTO;
import com.psitaland.exception.BusinessRuleException;
import com.psitaland.exception.ResourceNotFoundException;
import com.psitaland.models.Mutacao;
import com.psitaland.repositories.MutacaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Serviço responsável pela lógica de negócio de Mutacao.
 * Arquitetura em camadas:
 * - Recebe DTOs do Controller
 * - Aplica regras de negócio
 * - Persiste via Repository
 * - Devolve DTOs ao Controller
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MutacaoService {

    private final MutacaoRepository mutacaoRepository;

    // ----------------------------------------------------------------
    // Listar todas
    // ----------------------------------------------------------------

    @Transactional(readOnly = true)
    public List<MutacaoResponseDTO> listarTodas() {
        log.info("Listando todas as mutações.");
        return mutacaoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // ----------------------------------------------------------------
    // Buscar por ID
    // ----------------------------------------------------------------

    @Transactional(readOnly = true)
    public MutacaoResponseDTO buscarPorId(Integer id) {
        log.info("Buscando mutação com id {}.", id);
        Mutacao mutacao = buscarEntidadePorId(id);
        return toResponseDTO(mutacao);
    }

    // ----------------------------------------------------------------
    // Criar
    // ----------------------------------------------------------------

    @Transactional
    public MutacaoResponseDTO criar(MutacaoRequestDTO dto) {
        log.info("Criando mutação com descrição '{}'.", dto.getDescricao());

        validarDescricaoDuplicada(dto.getDescricao(), null);

        Mutacao mutacao = new Mutacao();
        mutacao.setDescricao(dto.getDescricao());

        Mutacao salva = mutacaoRepository.save(mutacao);
        log.info("Mutação criada com id {}.", salva.getId());

        return toResponseDTO(salva);
    }

    // ----------------------------------------------------------------
    // Atualizar
    // ----------------------------------------------------------------

    @Transactional
    public MutacaoResponseDTO atualizar(Integer id, MutacaoRequestDTO dto) {
        log.info("Atualizando mutação com id {}.", id);

        Mutacao mutacao = buscarEntidadePorId(id);
        validarDescricaoDuplicada(dto.getDescricao(), id);

        mutacao.setDescricao(dto.getDescricao());

        Mutacao atualizada = mutacaoRepository.save(mutacao);
        log.info("Mutação com id {} atualizada.", id);

        return toResponseDTO(atualizada);
    }

    // ----------------------------------------------------------------
    // Excluir
    // ----------------------------------------------------------------

    @Transactional
    public void excluir(Integer id) {
        log.info("Excluindo mutação com id {}.", id);
        Mutacao mutacao = buscarEntidadePorId(id);
        mutacaoRepository.delete(mutacao);
        log.info("Mutação com id {} excluída.", id);
    }

    // ----------------------------------------------------------------
    // Métodos auxiliares
    // ----------------------------------------------------------------

    public Mutacao buscarEntidadePorId(Integer id) {
        return mutacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mutacao", id));
    }

    private void validarDescricaoDuplicada(String descricao, Integer idAtual) {
        mutacaoRepository.findByDescricaoIgnoreCase(descricao).ifPresent(existente -> {
            if (!existente.getId().equals(idAtual)) {
                throw new BusinessRuleException("Já existe uma mutação cadastrada com a descrição '" + descricao + "'.");
            }
        });
    }

    private MutacaoResponseDTO toResponseDTO(Mutacao mutacao) {
        return new MutacaoResponseDTO(mutacao.getId(), mutacao.getDescricao());
    }

}
