package com.elisariane.votacao.controller;

import com.elisariane.votacao.dto.PautaDto;
import com.elisariane.votacao.exception.ApiErrorResponse;
import com.elisariane.votacao.model.Pauta;
import com.elisariane.votacao.model.SessaoVotacao;
import com.elisariane.votacao.service.PautaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/pauta")
@Tag(name = "pautas", description = "Operações relacionadas a pautas de votação")
public class PautaController {

    private final PautaService pautaService;

    public PautaController(PautaService pautaService) {
        this.pautaService = pautaService;
    }

    @Operation(summary = "Cria uma nova pauta", description = "Cria uma pauta a ser votada",
    responses = {
            @ApiResponse(responseCode = "201", description = "Pauta criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<Pauta> criar(@RequestBody @Valid PautaDto pautaDto) {
        Pauta novaPauta = pautaService.criar(pautaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaPauta);
    }

    @Operation(summary = "Lista todas as pautas criadas", description = "Retorna todas as pautas já criadas")
    @ApiResponse(responseCode = "200", description = "Lista de pautas",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Pauta.class)))
    @GetMapping
    public ResponseEntity<List<Pauta>> listarTodas() {
        List<Pauta> lista = pautaService.listarTodas();
        return ResponseEntity.ok(lista);
    }

}
