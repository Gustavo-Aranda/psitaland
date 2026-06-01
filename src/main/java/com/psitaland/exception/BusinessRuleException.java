package com.psitaland.exception;

/**
 * Exceção lançada quando uma operação viola uma regra de negócio do sistema.
 *
 * Exemplos de uso no sistema:
 * - Tentar mover um pássaro para a gaiola onde ele já está
 * - Tentar cadastrar uma anilha que já existe no plantel
 * - Tentar cadastrar uma gaiola com número já existente
 *
 * Resulta em resposta HTTP 409 Conflict.
 *
 * Diferença entre ResourceNotFoundException e BusinessRuleException:
 * - ResourceNotFoundException: o recurso NÃO EXISTE (404)
 * - BusinessRuleException: o recurso existe, mas a OPERAÇÃO não é permitida (409)
 */
public class BusinessRuleException extends RuntimeException {

    /**
     * @param mensagem  Descrição clara da regra violada.
     *
     * Exemplos:
     * "O pássaro já está na gaiola G001."
     * "Já existe uma ave cadastrada com a anilha A1234."
     */
    public BusinessRuleException(String mensagem) {
        super(mensagem);
    }

}
