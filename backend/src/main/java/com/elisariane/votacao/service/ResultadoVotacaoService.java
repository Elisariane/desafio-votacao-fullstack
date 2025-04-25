package com.elisariane.votacao.service;

import com.elisariane.votacao.dto.ResultadoVotacaoDto;
import com.elisariane.votacao.repository.PautaRepository;
import com.elisariane.votacao.repository.VotoRepository;
import enums.ResultadoVotacao;
import enums.TipoVoto;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ResultadoVotacaoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResultadoVotacaoService.class);
    private final PautaRepository pautaRepository;
    private final VotoRepository votoRepository;

    public ResultadoVotacaoService(PautaRepository pautaRepository,
                                   VotoRepository votoRepository) {
        this.pautaRepository = pautaRepository;
        this.votoRepository = votoRepository;
    }

    public ResultadoVotacaoDto apurarResultado(Long pautaId) {
        pautaRepository.findById(pautaId)
                .orElseThrow(() -> {
                    LOGGER.error("Pauta id {} não existe", pautaId);
                    return new EntityNotFoundException("Pauta não encontrada");
                });

        long sim = votoRepository.countBySessao_Pauta_IdAndTipoVoto(pautaId, TipoVoto.SIM);
        long nao = votoRepository.countBySessao_Pauta_IdAndTipoVoto(pautaId, TipoVoto.NAO);

        ResultadoVotacao res = sim > nao ? ResultadoVotacao.APROVADO : nao > sim ? ResultadoVotacao.REJEITADO : ResultadoVotacao.EMPATE;

        LOGGER.info("Resultado apurado com sucesso. Resultado da votação {}", res);

        return new ResultadoVotacaoDto(pautaId, sim, nao, res);
    }
}