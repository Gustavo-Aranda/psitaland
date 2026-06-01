package com.psitaland.services;

import com.psitaland.dtos.especie.EspecieResponseDTO;
import com.psitaland.dtos.gaiola.GaiolaResponseDTO;
import com.psitaland.dtos.mutacao.MutacaoResponseDTO;
import com.psitaland.dtos.passaro.PassaroRequestDTO;
import com.psitaland.dtos.passaro.PassaroResponseDTO;
import com.psitaland.dtos.status.StatusResponseDTO;
import com.psitaland.exception.BusinessRuleException;
import com.psitaland.exception.ResourceNotFoundException;
import com.psitaland.models.Especie;
import com.psitaland.models.Gaiola;
import com.psitaland.models.Mutacao;
import com.psitaland.models.Passaro;
import com.psitaland.models.Status;
import com.psitaland.repositories.PassaroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Serviço responsável pela lógica de negócio de Passaro.
 *
 * É o serviço mais complexo do sistema pois coordena
 * quatro relacionamentos (Especie, Mutacao, Gaiola, Status).
 *
 * Arquitetura em camadas:
 * - Recebe DTOs do Controller
 * - Valida regras de negócio
 * - Coordena com outros Services para resolver relacionamentos
 * - Persiste via Repository
 * - Devolve DTOs ao Controller
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PassaroService {

    private final PassaroRepository passaroRepository;

    // Injeção dos outros Services para resolver os relacionamentos
    // Princípio: o PassaroService não acessa repositórios de outras entidades diretamente
    private final EspecieService especieService;
    private final MutacaoService mutacaoService;
    private final GaiolaService gaiolaService;
    private final StatusService statusService;

    // ----------------------------------------------------------------
    // Listar todos
    // ----------------------------------------------------------------

    @Transactional(readOnly = true)
    public List<PassaroResponseDTO> listarTodos() {
        log.info("Listando todos os pássaros do plantel.");
        return passaroRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // ----------------------------------------------------------------
    // Buscar por ID
    // ----------------------------------------------------------------

    @Transactional(readOnly = true)
    public PassaroResponseDTO buscarPorId(Integer id) {
        log.info("Buscando pássaro com id {}.", id);
        Passaro passaro = buscarEntidadePorId(id);
        return toResponseDTO(passaro);
    }

    // ----------------------------------------------------------------
    // Buscar por anilha
    // ----------------------------------------------------------------

    @Transactional(readOnly = true)
    public PassaroResponseDTO buscarPorAnilha(String anilha) {
        log.info("Buscando pássaro com anilha '{}'.", anilha);
        Passaro passaro = passaroRepository.findByAnilha(anilha)
                .orElseThrow(() -> new ResourceNotFoundException("Passaro", "anilha", anilha));
        return toResponseDTO(passaro);
    }

    // ----------------------------------------------------------------
    // Consultas de negócio
    // ----------------------------------------------------------------

    @Transactional(readOnly = true)
    public List<PassaroResponseDTO> listarPorEspecie(Integer especieId) {
        log.info("Listando pássaros da espécie id {}.", especieId);
        // Valida se a espécie existe antes de consultar
        especieService.buscarEntidadePorId(especieId);
        return passaroRepository.findByEspecieId(especieId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PassaroResponseDTO> listarPorGaiola(Integer gaiolaId) {
        log.info("Listando pássaros da gaiola id {}.", gaiolaId);
        gaiolaService.buscarEntidadePorId(gaiolaId);
        return passaroRepository.findByGaiolaId(gaiolaId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PassaroResponseDTO> listarPorStatus(Integer statusId) {
        log.info("Listando pássaros com status id {}.", statusId);
        statusService.buscarEntidadePorId(statusId);
        return passaroRepository.findByStatusId(statusId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PassaroResponseDTO> listarPorMutacao(Integer mutacaoId) {
        log.info("Listando pássaros com mutação id {}.", mutacaoId);
        mutacaoService.buscarEntidadePorId(mutacaoId);
        return passaroRepository.findByMutacaoId(mutacaoId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // ----------------------------------------------------------------
    // Criar
    // ----------------------------------------------------------------

    @Transactional
    public PassaroResponseDTO criar(PassaroRequestDTO dto) {
        log.info("Cadastrando pássaro com anilha '{}'.", dto.getAnilha());

        validarAnilhaDuplicada(dto.getAnilha(), null);

        // Resolve os relacionamentos — lança 404 se qualquer ID não existir
        Especie especie = especieService.buscarEntidadePorId(dto.getEspecieId());
        Mutacao mutacao = mutacaoService.buscarEntidadePorId(dto.getMutacaoId());
        Gaiola  gaiola  = gaiolaService.buscarEntidadePorId(dto.getGaiolaId());
        Status  status  = statusService.buscarEntidadePorId(dto.getStatusId());

        Passaro passaro = new Passaro();
        passaro.setAnilha(dto.getAnilha());
        passaro.setDataNascimento(dto.getDataNascimento());
        passaro.setSexo(dto.getSexo());
        passaro.setNotaFiscal(dto.getNotaFiscal());
        passaro.setEspecie(especie);
        passaro.setMutacao(mutacao);
        passaro.setGaiola(gaiola);
        passaro.setStatus(status);

        Passaro salvo = passaroRepository.save(passaro);
        log.info("Pássaro cadastrado com id {}.", salvo.getId());

        return toResponseDTO(salvo);
    }

    // ----------------------------------------------------------------
    // Atualizar
    // ----------------------------------------------------------------

    @Transactional
    public PassaroResponseDTO atualizar(Integer id, PassaroRequestDTO dto) {
        log.info("Atualizando pássaro com id {}.", id);

        Passaro passaro = buscarEntidadePorId(id);
        validarAnilhaDuplicada(dto.getAnilha(), id);

        Especie especie = especieService.buscarEntidadePorId(dto.getEspecieId());
        Mutacao mutacao = mutacaoService.buscarEntidadePorId(dto.getMutacaoId());
        Gaiola  gaiola  = gaiolaService.buscarEntidadePorId(dto.getGaiolaId());
        Status  status  = statusService.buscarEntidadePorId(dto.getStatusId());

        passaro.setAnilha(dto.getAnilha());
        passaro.setDataNascimento(dto.getDataNascimento());
        passaro.setSexo(dto.getSexo());
        passaro.setNotaFiscal(dto.getNotaFiscal());
        passaro.setEspecie(especie);
        passaro.setMutacao(mutacao);
        passaro.setGaiola(gaiola);
        passaro.setStatus(status);

        Passaro atualizado = passaroRepository.save(passaro);
        log.info("Pássaro com id {} atualizado.", id);

        return toResponseDTO(atualizado);
    }

    // ----------------------------------------------------------------
    // Mover para outra gaiola
    // ----------------------------------------------------------------

    /**
     * Move um pássaro de uma gaiola para outra.
     *
     * Regras de negócio:
     * 1. O pássaro deve existir → 404 se não existir
     * 2. A gaiola de destino deve existir → 404 se não existir
     * 3. O pássaro não pode ser movido para a gaiola onde já está → 409
     */
    @Transactional
    public PassaroResponseDTO mover(Integer passaroId, Integer gaiolaDestinoId) {
        log.info("Movendo pássaro id {} para gaiola id {}.", passaroId, gaiolaDestinoId);

        Passaro passaro = buscarEntidadePorId(passaroId);
        Gaiola gaiolaDestino = gaiolaService.buscarEntidadePorId(gaiolaDestinoId);

        if (passaro.getGaiola().getId().equals(gaiolaDestinoId)) {
            throw new BusinessRuleException(
                "O pássaro já está na gaiola '" + gaiolaDestino.getNumero() + "'."
            );
        }

        String gaiolaOrigem = passaro.getGaiola().getNumero();
        passaro.setGaiola(gaiolaDestino);
        passaroRepository.save(passaro);

        log.info("Pássaro id {} movido da gaiola '{}' para '{}'.",
                passaroId, gaiolaOrigem, gaiolaDestino.getNumero());

        return toResponseDTO(passaro);
    }

    // ----------------------------------------------------------------
    // Excluir
    // ----------------------------------------------------------------

    @Transactional
    public void excluir(Integer id) {
        log.info("Excluindo pássaro com id {}.", id);
        Passaro passaro = buscarEntidadePorId(id);
        passaroRepository.delete(passaro);
        log.info("Pássaro com id {} excluído.", id);
    }

    // ----------------------------------------------------------------
    // Métodos auxiliares
    // ----------------------------------------------------------------

    public Passaro buscarEntidadePorId(Integer id) {
        return passaroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passaro", id));
    }

    private void validarAnilhaDuplicada(String anilha, Integer idAtual) {
        passaroRepository.findByAnilha(anilha).ifPresent(existente -> {
            if (!existente.getId().equals(idAtual)) {
                throw new BusinessRuleException(
                    "Já existe um pássaro cadastrado com a anilha '" + anilha + "'."
                );
            }
        });
    }

    /**
     * Converte entidade Passaro → PassaroResponseDTO.
     *
     * Padrão DTO: monta o objeto de resposta com todos os relacionamentos
     * já convertidos para seus respectivos ResponseDTOs.
     * A entidade nunca sai da camada de serviço.
     */
    private PassaroResponseDTO toResponseDTO(Passaro p) {
        return new PassaroResponseDTO(
                p.getId(),
                p.getAnilha(),
                p.getDataNascimento(),
                p.getSexo(),
                p.getNotaFiscal(),
                new EspecieResponseDTO(p.getEspecie().getId(), p.getEspecie().getNome()),
                new MutacaoResponseDTO(p.getMutacao().getId(), p.getMutacao().getDescricao()),
                new GaiolaResponseDTO(p.getGaiola().getId(), p.getGaiola().getNumero()),
                new StatusResponseDTO(p.getStatus().getId(), p.getStatus().getSituacao())
        );
    }

}
