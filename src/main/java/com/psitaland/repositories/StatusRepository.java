package com.psitaland.repositories;

import com.psitaland.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório de acesso ao banco para a entidade Status.
 *
 * Padrão Repository: abstrai completamente o acesso ao banco.
 */
@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {

    /**
     * Busca um status pela situação, ignorando maiúsculas/minúsculas.
     * Usado para verificar duplicidade antes de cadastrar.
     */
    Optional<Status> findBySituacaoIgnoreCase(String situacao);

}
