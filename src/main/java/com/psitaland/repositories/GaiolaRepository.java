package com.psitaland.repositories;

import com.psitaland.models.Gaiola;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório de acesso ao banco para a entidade Gaiola.
 *
 * Padrão Repository: abstrai completamente o acesso ao banco.
 */
@Repository
public interface GaiolaRepository extends JpaRepository<Gaiola, Integer> {

    /**
     * Busca uma gaiola pelo número, ignorando maiúsculas/minúsculas.
     * Usado para verificar duplicidade antes de cadastrar.
     * Ex: "g001" e "G001" são considerados o mesmo número.
     */
    Optional<Gaiola> findByNumeroIgnoreCase(String numero);

}
