package com.elisariane.votacao.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Schema(name = "SessaoVotacaoDto", description = "DTO para abrir uma sessão de votação")
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
        LocalDateTime inicio,

        @Schema(
                description = "Data e hora de fim da sessão (se não informado, padrão 1 minuto após o início)",
                example = "2025-04-24T16:31:00"
        )
        LocalDateTime fim

) {
}
