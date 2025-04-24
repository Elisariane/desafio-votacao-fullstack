package com.elisariane.votacao.controller;

import com.elisariane.votacao.dto.AssociadoDto;
import com.elisariane.votacao.exception.ApiErrorResponse;
import com.elisariane.votacao.model.Associado;
import com.elisariane.votacao.service.AssociadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/associados")
@Tag(name = "associados", description = "Operações relacionadas a associados")
public class AssociadoController {

    private final AssociadoService associadoService;

    public AssociadoController(AssociadoService associadoService) {
        this.associadoService = associadoService;
    }

    @Operation(summary = "Cadastra um novo associado", description = "Registra um novo associado com nome e CPF único",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Associado cadastrado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de validação ou CPF já existente",
                            content = @io.swagger.v3.oas.annotations.media.Content(
                                    mediaType = "application/json",
                                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ApiErrorResponse.class)
                            ))
            })
    @PostMapping
    public ResponseEntity<Associado> criar(@RequestBody @Valid AssociadoDto associadoDto) {
        Associado associado = associadoService.criar(associadoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(associado);
    }

    @Operation(summary = "Lista todos os associados cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<Associado>> listar() {
        return ResponseEntity.ok(associadoService.listarTodos());
    }
}