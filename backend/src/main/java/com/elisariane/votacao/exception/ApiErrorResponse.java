package com.elisariane.votacao.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Resposta de erro da API")
public class ApiErrorResponse {

    @Schema(description = "Timestamp do erro", example = "2025-04-24T17:02:00Z")
    private LocalDateTime timestamp;

    @Schema(description = "Código HTTP", example = "400")
    private int status;

    @Schema(description = "Tipo do erro", example = "Erro de validação")
    private String error;

    @Schema(description = "Rota da url onde ocorreu a requisição", example = "/v1/pauta")
    private String path;

    @Schema(description = "Lista de mensagens detalhadas")
    private List<String> mensagens;

    public ApiErrorResponse() {}

    public ApiErrorResponse(int status, String error, String path, List<String> mensagens) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.path = path;
        this.mensagens = mensagens;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<String> mensagens) {
        this.mensagens = mensagens;
    }
}
