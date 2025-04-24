package com.elisariane.votacao.controller;

import com.elisariane.votacao.dto.SessaoVotacaoDto;
import com.elisariane.votacao.exception.ApiErrorResponse;
import com.elisariane.votacao.model.SessaoVotacao;
import com.elisariane.votacao.service.SessaoVotacaoService;
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
@RequestMapping("/v1/sessoes")
@Tag(name = "Sessões de Votação", description = "Operações para gerenciar sessões de votação")
public class SessaoVotacaoController {

    private final SessaoVotacaoService service;

    public SessaoVotacaoController(SessaoVotacaoService service) {
        this.service = service;
    }

    @Operation(summary = "Abre uma nova sessão de votação", description = "Cria uma sessão para uma pauta, com duração informada ou 1 minuto por padrão",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Sessão criada com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SessaoVotacao.class))),
                    @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Pauta não encontrada",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class))),
                    @ApiResponse(responseCode = "409", description = "Já existe uma sessão aberta para esta pauta",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @PostMapping
    public ResponseEntity<SessaoVotacao> abrirSessao(
            @RequestBody @Valid SessaoVotacaoDto dto) {
        SessaoVotacao criada = service.abrirSessaoVotacao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    @Operation(summary = "Lista todas as sessões de votação", description = "Retorna todas as sessões já criadas")
    @ApiResponse(responseCode = "200", description = "Lista de sessões",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = SessaoVotacao.class)))
    @GetMapping
    public ResponseEntity<List<SessaoVotacao>> listarTodas() {
        List<SessaoVotacao> lista = service.listarTodas();  // implemente este método no service
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Busca uma sessão por ID", description = "Retorna os detalhes de uma sessão específica",
    responses = {
        @ApiResponse(responseCode = "200", description = "Sessão encontrada",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = SessaoVotacao.class))),
        @ApiResponse(responseCode = "404", description = "Sessão não encontrada",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<SessaoVotacao> buscarPorId(
            @PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}