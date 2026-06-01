package com.psitaland.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * Tratamento CENTRALIZADO de exceções da aplicação.
 *
 * @RestControllerAdvice intercepta exceções lançadas em qualquer
 * Controller da aplicação e transforma em respostas HTTP padronizadas.
 *
 * Benefício arquitetural:
 * Sem este handler, cada Controller precisaria de try/catch para tratar
 * erros individualmente. Com ele, os Controllers ficam limpos e focados
 * apenas em receber e delegar requisições.
 *
 * Exceções tratadas:
 * - ResourceNotFoundException  → 404 Not Found
 * - BusinessRuleException      → 409 Conflict
 * - MethodArgumentNotValidException → 400 Bad Request (Bean Validation)
 * - Exception (genérica)       → 500 Internal Server Error
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ----------------------------------------------------------------
    // 404 - Recurso não encontrado
    // ----------------------------------------------------------------

    /**
     * Intercepta ResourceNotFoundException lançada nos Services.
     * Retorna 404 com mensagem indicando qual recurso não foi encontrado.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        log.warn("Recurso não encontrado: {} | Caminho: {}", ex.getMessage(), request.getRequestURI());

        ApiError erro = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                "Recurso não encontrado",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    // ----------------------------------------------------------------
    // 409 - Conflito / Regra de negócio violada
    // ----------------------------------------------------------------

    /**
     * Intercepta BusinessRuleException lançada nos Services.
     * Retorna 409 com mensagem descrevendo a regra violada.
     *
     * Exemplo: tentar mover um pássaro para a gaiola onde ele já está.
     */
    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ApiError> handleBusinessRule(
            BusinessRuleException ex,
            HttpServletRequest request) {

        log.warn("Regra de negócio violada: {} | Caminho: {}", ex.getMessage(), request.getRequestURI());

        ApiError erro = new ApiError(
                HttpStatus.CONFLICT.value(),
                "Conflito de regra de negócio",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    // ----------------------------------------------------------------
    // 400 - Erro de validação (Bean Validation)
    // ----------------------------------------------------------------

    /**
     * Intercepta falhas de validação do @Valid nos Controllers.
     * Retorna 400 com a lista de TODOS os campos inválidos de uma vez.
     *
     * Exemplo: cliente enviou PassaroRequestDTO sem anilha e com data futura.
     * A resposta listará os dois erros simultaneamente.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        // Coleta todas as mensagens de erro de campo
        List<String> errosCampos = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .sorted()
                .toList();

        log.warn("Erro de validação em {}: {}", request.getRequestURI(), errosCampos);

        ApiError erro = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação",
                "Um ou mais campos são inválidos.",
                request.getRequestURI(),
                errosCampos
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    // ----------------------------------------------------------------
    // 500 - Erro interno inesperado
    // ----------------------------------------------------------------

    /**
     * Intercepta qualquer exceção não tratada especificamente acima.
     * Retorna 500 sem expor detalhes internos ao cliente.
     *
     * O log registra a exceção completa para diagnóstico.
     * O cliente recebe apenas uma mensagem genérica — nunca stack traces.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(
            Exception ex,
            HttpServletRequest request) {

        log.error("Erro interno inesperado em {}: {}", request.getRequestURI(), ex.getMessage(), ex);

        ApiError erro = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno do servidor",
                "Ocorreu um erro inesperado. Tente novamente mais tarde.",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }

}
