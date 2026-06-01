package com.psitaland.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Representa o corpo padronizado de TODAS as respostas de erro da API.
 *
 * Padrão DTO aplicado ao tratamento de erros:
 * em vez de devolver exceções Java cruas (com stack trace),
 * a API sempre devolve este objeto estruturado.
 *
 * Exemplo de resposta para um recurso não encontrado:
 * {
 *   "timestamp": "2024-06-15 14:30:00",
 *   "status": 404,
 *   "erro": "Recurso não encontrado",
 *   "mensagem": "Passaro com id 99 não encontrado.",
 *   "caminho": "/passaros/99"
 * }
 *
 * Exemplo de resposta para erro de validação:
 * {
 *   "timestamp": "2024-06-15 14:30:00",
 *   "status": 400,
 *   "erro": "Erro de validação",
 *   "mensagem": "Um ou mais campos são inválidos.",
 *   "caminho": "/passaros",
 *   "errosCampos": [
 *     "A anilha é obrigatória.",
 *     "A data de nascimento deve ser uma data no passado."
 *   ]
 * }
 */
@Getter
public class ApiError {

    /**
     * Momento exato em que o erro ocorreu.
     * Formato: "dd/MM/yyyy HH:mm:ss"
     */
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private final LocalDateTime timestamp;

    /** Código HTTP do erro. Ex: 400, 404, 409, 500 */
    private final int status;

    /** Título curto do erro. Ex: "Recurso não encontrado" */
    private final String erro;

    /** Mensagem detalhada explicando o que aconteceu */
    private final String mensagem;

    /** Endpoint que gerou o erro. Ex: "/passaros/99" */
    private final String caminho;

    /**
     * Lista de erros por campo — preenchida apenas em erros de validação (400).
     * Nula em outros tipos de erro (não serializada no JSON por configuração).
     */
    private final List<String> errosCampos;

    /**
     * Construtor para erros simples (404, 409, 500).
     */
    public ApiError(int status, String erro, String mensagem, String caminho) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.erro = erro;
        this.mensagem = mensagem;
        this.caminho = caminho;
        this.errosCampos = null;
    }

    /**
     * Construtor para erros de validação (400) com lista de campos inválidos.
     */
    public ApiError(int status, String erro, String mensagem, String caminho, List<String> errosCampos) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.erro = erro;
        this.mensagem = mensagem;
        this.caminho = caminho;
        this.errosCampos = errosCampos;
    }

}
