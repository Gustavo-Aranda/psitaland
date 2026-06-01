package com.psitaland.repositories;

import com.psitaland.models.Especie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório de acesso ao banco para a entidade Especie.
 *
 * Padrão Repository: abstrai completamente o acesso ao banco.
 * Nenhuma linha de SQL é necessária para as operações básicas —
 * o Spring Data JPA gera tudo automaticamente.
 *
 * O método findByNomeIgnoreCase é gerado por convenção de nomenclatura
 * do Spring Data — sem necessidade de escrever JPQL ou SQL.
 */
@Repository
public interface EspecieRepository extends JpaRepository<Especie, Integer> {

    /**
     * Busca uma espécie pelo nome, ignorando maiúsculas/minúsculas.
     * Usado para verificar duplicidade antes de cadastrar.
     * Ex: "calopsita" e "Calopsita" são considerados iguais.
     */
    Optional<Especie> findByNomeIgnoreCase(String nome);

}
