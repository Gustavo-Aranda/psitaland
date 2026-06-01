package com.psitaland.repositories;

import com.psitaland.models.Passaro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório de acesso ao banco para a entidade Passaro.
 *
 * Padrão Repository: abstrai completamente o acesso ao banco.
 *
 * Todos os métodos abaixo são gerados automaticamente pelo Spring Data JPA
 * por convenção de nomenclatura — sem JPQL ou SQL escrito manualmente.
 *
 * Convenção:
 * findBy{Campo}           → busca por campo da própria entidade
 * findBy{Relacionamento}  → busca por campo de entidade relacionada
 * IgnoreCase              → ignora maiúsculas/minúsculas
 */
@Repository
public interface PassaroRepository extends JpaRepository<Passaro, Integer> {

    /**
     * Busca um pássaro pela anilha.
     * A anilha é o identificador físico único da ave (anel na pata).
     */
    Optional<Passaro> findByAnilha(String anilha);

    /**
     * Lista todos os pássaros de uma espécie específica.
     * Ex: listar todas as Calopsitas do plantel.
     */
    List<Passaro> findByEspecieId(Integer especieId);

    /**
     * Lista todos os pássaros em uma gaiola específica.
     * Permite visualizar quais aves estão em cada localização física.
     */
    List<Passaro> findByGaiolaId(Integer gaiolaId);

    /**
     * Lista todos os pássaros com um status específico.
     * Ex: listar todas as aves com status "Ativo" ou "Vendido".
     */
    List<Passaro> findByStatusId(Integer statusId);

    /**
     * Lista todos os pássaros com uma mutação específica.
     * Ex: listar todas as aves com mutação "Lutino".
     */
    List<Passaro> findByMutacaoId(Integer mutacaoId);

    /**
     * Verifica se já existe um pássaro cadastrado com a anilha informada.
     * Usado para garantir unicidade da anilha no plantel.
     */
    boolean existsByAnilha(String anilha);

}
