package com.elisariane.votacao.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

@Schema(name = "AssociadoDto", description = "DTO para criar uma novo associado")
public record AssociadoDto(

        @NotBlank(message = "O nome do associado não pode ser nulo ou estar em branco")
        @Schema(description = "Nome completo do associado", example = "Murilo Joaquim Novaes")
        String nome,

        @CPF(message = "Informe um CPF válido" )
        @Schema(description = "CPF do associado (sem pontuações)", example = "62286191018")
        String cpf
) {
}
