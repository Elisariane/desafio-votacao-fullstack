package com.elisariane.votacao.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                         HttpServletRequest request) {
        List<String> erros = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .toList();

        LOGGER.warn("Validação falhou em {}: {}", request.getRequestURI(), erros);
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Erro de validação", request.getRequestURI(), erros);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolation(ConstraintViolationException ex,
                                                                      HttpServletRequest request) {
        List<String> erros = ex.getConstraintViolations().stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .toList();

        LOGGER.warn("ConstraintViolation em {}: {}", request.getRequestURI(), erros);
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Violação de restrição", request.getRequestURI(), erros);
    }

    @ExceptionHandler(PautaJaPossuiSessaoAbertaException.class)
    public ResponseEntity<ApiErrorResponse> handleSessaoJaAberta(PautaJaPossuiSessaoAbertaException ex,
                                                                 HttpServletRequest request) {
        String msg = ex.getMessage();
        LOGGER.info("Conflito de sessão para {}: {}", request.getRequestURI(), msg);
        return buildErrorResponse(HttpStatus.CONFLICT, "Conflito de sessão", request.getRequestURI(), List.of(msg));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneric(Exception ex,
                                                          HttpServletRequest request) {
        LOGGER.error("Erro interno em " + request.getRequestURI(), ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno", request.getRequestURI(), List.of(ex.getMessage()));
    }

    private ResponseEntity<ApiErrorResponse> buildErrorResponse(HttpStatus status, String erro,
                                                                String path, List<String> mensagens) {
        ApiErrorResponse resposta = new ApiErrorResponse(
                status.value(),
                erro,
                path,
                mensagens
        );
        return ResponseEntity.status(status).body(resposta);
    }
}
