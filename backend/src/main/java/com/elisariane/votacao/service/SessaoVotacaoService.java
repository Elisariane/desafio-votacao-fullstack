package com.elisariane.votacao.service;

import com.elisariane.votacao.dto.SessaoVotacaoDto;
import com.elisariane.votacao.exception.PautaJaPossuiSessaoAbertaException;
import com.elisariane.votacao.model.Pauta;
import com.elisariane.votacao.model.SessaoVotacao;
import com.elisariane.votacao.repository.PautaRepository;
import com.elisariane.votacao.repository.SessaoVotacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SessaoVotacaoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessaoVotacaoService.class);
    private final PautaRepository pautaRepository;
    private final SessaoVotacaoRepository sessaoVotacaoRepository;

    public SessaoVotacaoService(PautaRepository pautaRepository, SessaoVotacaoRepository sessaoVotacaoRepository) {
        this.pautaRepository = pautaRepository;
        this.sessaoVotacaoRepository = sessaoVotacaoRepository;
    }

    public SessaoVotacao abrirSessaoVotacao(SessaoVotacaoDto dto) {

        LOGGER.info("Buscando pauta id {}", dto.pautaId());

        Pauta pauta = pautaRepository.findById(dto.pautaId())
                .orElseThrow(() -> {
                    LOGGER.error("Pauta id {} não existe", dto.pautaId());
                    return new EntityNotFoundException("Pauta não encontrada");
                });

        boolean existeSessaoAberta = sessaoVotacaoRepository.existsActiveSessionByPauta(pauta);

        if (existeSessaoAberta) {
            LOGGER.warn("Já existe uma sessão ativa para a pauta {}", pauta.getId());
            throw new PautaJaPossuiSessaoAbertaException(dto.pautaId());
        }

        LOGGER.info("Iniciando abertura de sessão para pauta ID {}", dto.pautaId());

        ZonedDateTime inicio = dto.inicio() != null ? dto.inicio() : ZonedDateTime.now().minusSeconds(1);
        ZonedDateTime fim    = dto.fim()    != null ? dto.fim() : inicio.plusMinutes(1);

        SessaoVotacao sessao = new SessaoVotacao(pauta, inicio, fim);

        SessaoVotacao sessaoSalva = sessaoVotacaoRepository.save(sessao);

        LOGGER.info("Sessão criada com início em {} e fim em {}", inicio, fim);

        return sessaoSalva;
    }

    public List<SessaoVotacao> listarTodas() {
        LOGGER.info("Buscando todas sessões de votação...");
        return sessaoVotacaoRepository.findAll();
    }

    public Optional<SessaoVotacao> buscarPorId(Long id) {
        LOGGER.info("Busca por sessão de id {}", id);
        return Optional.ofNullable(sessaoVotacaoRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Sessão de votação id {} não existe", id);
                    return new EntityNotFoundException("Sessão de votação não encontrada");
                }));
    }

}
