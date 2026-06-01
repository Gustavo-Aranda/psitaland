package com.psitaland.repositories;

import com.psitaland.models.Mutacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório de acesso ao banco para a entidade Mutacao.
 *
 * Padrão Repository: abstrai completamente o acesso ao banco.
 */
@Repository
public interface MutacaoRepository extends JpaRepository<Mutacao, Integer> {

    /**
     * Busca uma mutação pela descrição, ignorando maiúsculas/minúsculas.
     * Usado para verificar duplicidade antes de cadastrar.
     */
    Optional<Mutacao> findByDescricaoIgnoreCase(String descricao);

}
