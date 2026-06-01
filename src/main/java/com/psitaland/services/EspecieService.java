package com.psitaland.services;

import com.psitaland.dtos.especie.EspecieRequestDTO;
import com.psitaland.dtos.especie.EspecieResponseDTO;
import com.psitaland.exception.BusinessRuleException;
import com.psitaland.exception.ResourceNotFoundException;
import com.psitaland.models.Especie;
import com.psitaland.repositories.EspecieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Serviço responsável pela lógica de negócio de Especie.
 *
 * Arquitetura em camadas:
 * - Recebe DTOs do Controller
 * - Aplica regras de negócio
 * - Persiste via Repository
 * - Devolve DTOs ao Controller
 *
 * A entidade Especie nunca sai desta camada.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EspecieService {

    private final EspecieRepository especieRepository;

    // ----------------------------------------------------------------
    // Listar todas
    // ----------------------------------------------------------------

    @Transactional(readOnly = true)
    public List<EspecieResponseDTO> listarTodas() {
        log.info("Listando todas as espécies.");
        return especieRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // ----------------------------------------------------------------
    // Buscar por ID
    // ----------------------------------------------------------------

    @Transactional(readOnly = true)
    public EspecieResponseDTO buscarPorId(Integer id) {
        log.info("Buscando espécie com id {}.", id);
        Especie especie = buscarEntidadePorId(id);
        return toResponseDTO(especie);
    }

    // ----------------------------------------------------------------
    // Criar
    // ----------------------------------------------------------------

    @Transactional
    public EspecieResponseDTO criar(EspecieRequestDTO dto) {
        log.info("Criando espécie com nome '{}'.", dto.getNome());

        validarNomeDuplicado(dto.getNome(), null);

        Especie especie = new Especie();
        especie.setNome(dto.getNome());

        Especie salva = especieRepository.save(especie);
        log.info("Espécie criada com id {}.", salva.getId());

        return toResponseDTO(salva);
    }

    // ----------------------------------------------------------------
    // Atualizar
    // ----------------------------------------------------------------

    @Transactional
    public EspecieResponseDTO atualizar(Integer id, EspecieRequestDTO dto) {
        log.info("Atualizando espécie com id {}.", id);

        Especie especie = buscarEntidadePorId(id);
        validarNomeDuplicado(dto.getNome(), id);

        especie.setNome(dto.getNome());

        Especie atualizada = especieRepository.save(especie);
        log.info("Espécie com id {} atualizada.", id);

        return toResponseDTO(atualizada);
    }

    // ----------------------------------------------------------------
    // Excluir
    // ----------------------------------------------------------------

    @Transactional
    public void excluir(Integer id) {
        log.info("Excluindo espécie com id {}.", id);
        Especie especie = buscarEntidadePorId(id);
        especieRepository.delete(especie);
        log.info("Espécie com id {} excluída.", id);
    }

    // ----------------------------------------------------------------
    // Métodos auxiliares (uso interno e pelos outros Services)
    // ----------------------------------------------------------------

    /**
     * Busca a entidade diretamente — uso interno nos Services.
     * Lança 404 se não encontrar.
     */
    public Especie buscarEntidadePorId(Integer id) {
        return especieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Especie", id));
    }

    /**
     * Valida se já existe outra espécie com o mesmo nome.
     * O parâmetro idAtual permite ignorar a própria entidade no update.
     */
    private void validarNomeDuplicado(String nome, Integer idAtual) {
        especieRepository.findByNomeIgnoreCase(nome).ifPresent(existente -> {
            if (!existente.getId().equals(idAtual)) {
                throw new BusinessRuleException("Já existe uma espécie cadastrada com o nome '" + nome + "'.");
            }
        });
    }

    /**
     * Converte entidade Especie → EspecieResponseDTO.
     * Padrão DTO: a entidade nunca sai da camada de serviço.
     */
    private EspecieResponseDTO toResponseDTO(Especie especie) {
        return new EspecieResponseDTO(especie.getId(), especie.getNome());
    }

}
