package com.elisariane.votacao.dto;

import enums.ResultadoVotacao;

public record ResultadoVotacaoDto(

        Long pautaId,
        long totalSim,
        long totalNao,
        ResultadoVotacao resultadoVotacao

) {

}
