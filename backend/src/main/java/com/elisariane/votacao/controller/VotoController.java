package com.elisariane.votacao.controller;

import com.elisariane.votacao.dto.VotoDto;
import com.elisariane.votacao.exception.ApiErrorResponse;
import com.elisariane.votacao.model.Voto;
import com.elisariane.votacao.service.VotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/votos")
@Tag(name = "votos", description = "Operações relacionadas a votos em pautas")
public class VotoController {

    private final VotoService votoService;

    public VotoController(VotoService votoService) {
        this.votoService = votoService;
    }

    @Operation(
            summary = "Registra um novo voto",
            description = "Registra o voto (SIM ou NÃO) de um associado em uma sessão de votação",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Voto registrado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida ou voto duplicado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Erro interno",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @PostMapping
    public ResponseEntity<Voto> votar(@RequestBody @Valid VotoDto dto) {
        Voto voto = votoService.votar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(voto);
    }
}