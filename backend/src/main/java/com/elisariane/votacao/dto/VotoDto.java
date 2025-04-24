package com.elisariane.votacao.dto;

import enums.TipoVoto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "VotoDto", description = "DTO para registrar um voto")
public record VotoDto(

        @NotNull
        @Schema(description = "ID da sessão de votação", example = "1")
        Long sessaoId,

        @NotNull
        @Schema(description = "ID do associado que está votando", example = "2")
        Long associadoId,

        @NotNull
        @Schema(description = "Tipo de voto", example = "SIM")
        TipoVoto voto
) {}