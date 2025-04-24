package com.elisariane.votacao.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record PautaDto(

        @NotBlank(message = "Por favor defina um título para a pauta")
        @Schema(description = "Título da pauta", example = "Aprovação do orçamento anual")
        String titulo,
        @Schema(description = "Descrição da pauta", example = "Discussão e votação do orçamento previsto para o próximo exercício fiscal.")
        String descricao

) {
}
