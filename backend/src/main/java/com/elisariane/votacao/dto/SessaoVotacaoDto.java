package com.elisariane.votacao.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.ZonedDateTime;

@Schema(name = "SessaoVotacaoDto", description = "DTO para abrir uma sessão de votação")
@JsonIgnoreProperties(ignoreUnknown = true)
public record SessaoVotacaoDto(

        @NotNull(message = "É necessário informar o ID da pauta para abrir a sessão")
        @Schema(
                description = "ID da pauta que será votada",
                example = "42"
        )
        Long pautaId,

        @Schema(
                description = "Data e hora de início da sessão (será ignorado se não informado e gerado pelo servidor)",
                example = "2025-04-24T16:30:00"
        )
        ZonedDateTime inicio,

        @Schema(
                description = "Data e hora de fim da sessão (se não informado, padrão 1 minuto após o início)",
                example = "2025-04-24T16:31:00"
        )
        ZonedDateTime fim

) {
}
