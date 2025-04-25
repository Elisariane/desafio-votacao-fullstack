package com.elisariane.votacao.controller;

import com.elisariane.votacao.dto.ResultadoVotacaoDto;
import com.elisariane.votacao.exception.ApiErrorResponse;
import com.elisariane.votacao.service.ResultadoVotacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/resultados")
@Tag(name="resultados", description="Apuração de votos por pauta")
public class ResultadoVotacaoController {

    private final ResultadoVotacaoService service;

    public ResultadoVotacaoController(ResultadoVotacaoService service) {
        this.service = service;
    }

    @Operation(summary="Apurar resultado de uma pauta",
            description="Retorna total de votos SIM, NÃO e o veredito",
            responses = {
                    @ApiResponse(responseCode="200", description="Resultado obtido",
                            content=@Content(schema=@Schema(implementation= ResultadoVotacaoDto.class))),
                    @ApiResponse(responseCode="404", description="Pauta não encontrada",
                            content=@Content(schema=@Schema(implementation= ApiErrorResponse.class)))
            }
    )
    @GetMapping("/{pautaId}")
    public ResponseEntity<ResultadoVotacaoDto> apurar(@PathVariable Long pautaId) {
        ResultadoVotacaoDto dto = service.apurarResultado(pautaId);
        return ResponseEntity.ok(dto);
    }

}