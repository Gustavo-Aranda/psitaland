package com.psitaland.exception;

/**
 * Exceção lançada quando um recurso solicitado não existe no banco de dados.
 *
 * Exemplos de uso no sistema:
 * - Buscar um pássaro por ID que não existe
 * - Mover um pássaro para uma gaiola que não existe
 * - Atualizar uma espécie com ID inexistente
 *
 * Resulta em resposta HTTP 404 Not Found.
 *
 * Por que extends RuntimeException?
 * RuntimeException não obriga o código chamador a declarar "throws"
 * ou usar try/catch. O GlobalExceptionHandler intercepta automaticamente.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * @param recurso  Nome da entidade. Ex: "Passaro", "Gaiola"
     * @param id       ID que foi buscado e não encontrado
     *
     * Gera mensagem: "Passaro com id 42 não encontrado."
     */
    public ResourceNotFoundException(String recurso, Integer id) {
        super(recurso + " com id " + id + " não encontrado.");
    }

    /**
     * Construtor alternativo para buscas por campo não-ID.
     *
     * @param recurso  Nome da entidade. Ex: "Passaro"
     * @param campo    Nome do campo. Ex: "anilha"
     * @param valor    Valor buscado. Ex: "A1234"
     *
     * Gera mensagem: "Passaro com anilha 'A1234' não encontrado."
     */
    public ResourceNotFoundException(String recurso, String campo, String valor) {
        super(recurso + " com " + campo + " '" + valor + "' não encontrado.");
    }

}
