package com.elisariane.votacao.service;

import com.elisariane.votacao.client.CpfStatusResponse;
import com.elisariane.votacao.client.CpfValidationClient;
import com.elisariane.votacao.dto.VotoDto;
import com.elisariane.votacao.model.Associado;
import com.elisariane.votacao.model.SessaoVotacao;
import com.elisariane.votacao.model.Voto;
import com.elisariane.votacao.repository.AssociadoRepository;
import com.elisariane.votacao.repository.SessaoVotacaoRepository;
import com.elisariane.votacao.repository.VotoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VotoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VotoService.class);

    private final SessaoVotacaoRepository sessaoRepository;
    private final AssociadoRepository associadoRepository;
    private final VotoRepository votoRepository;
    private final CpfValidationClient cpfValidationClient;

    public VotoService(SessaoVotacaoRepository sessaoRepository,
                       AssociadoRepository associadoRepository,
                       VotoRepository votoRepository,
                       CpfValidationClient cpfValidationClient) {
        this.sessaoRepository = sessaoRepository;
        this.associadoRepository = associadoRepository;
        this.votoRepository = votoRepository;
        this.cpfValidationClient = cpfValidationClient;
    }

    public Voto votar(VotoDto dto) {
        LOGGER.info("Registrando voto do associado {} na sessão {}", dto.associadoId(), dto.sessaoId());

        SessaoVotacao sessao = sessaoRepository.findById(dto.sessaoId())
                .orElseThrow(() -> new EntityNotFoundException("Sessão não encontrada"));

        if (!sessao.isAtiva()) {
            LOGGER.warn("Tentativa de voto em sessão encerrada");
            throw new IllegalStateException("Sessão encerrada para votação");
        }

        Associado associado = associadoRepository.findById(dto.associadoId())
                .orElseThrow(() -> new EntityNotFoundException("Associado não encontrado"));

        // Valida se o associado pode votar
        CpfStatusResponse status = cpfValidationClient.validarCpf(associado.getCpf());

        if (!status.isAbleToVote()) {
            LOGGER.warn("Associado {} não pode votar (status externo)", associado.getId());
            throw new IllegalStateException("Associado não está autorizado a votar");
        }

        // Impede voto duplicado
        boolean jaVotou = votoRepository.existsBySessaoAndAssociado(sessao, associado);

        if (jaVotou) {
            LOGGER.warn("Associado {} já votou na sessão {}", associado.getId(), sessao.getId());
            throw new IllegalArgumentException("Associado já votou nessa pauta");
        }

        Voto voto = new Voto(sessao, associado, dto.voto());

        Voto salvo = votoRepository.save(voto);

        LOGGER.info("Voto registrado com sucesso");
        return salvo;
    }
}