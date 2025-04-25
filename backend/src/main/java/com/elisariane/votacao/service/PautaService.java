package com.elisariane.votacao.service;

import com.elisariane.votacao.dto.PautaDto;
import com.elisariane.votacao.model.Pauta;
import com.elisariane.votacao.repository.PautaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PautaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PautaService.class);
    private final PautaRepository pautaRepository;

    public PautaService(PautaRepository pautaRepository) {
        this.pautaRepository = pautaRepository;
    }

    public Pauta criar(PautaDto pautaDto) {
        LOGGER.info("Criando uma nova pauta ...");

        Pauta novaPauta = new Pauta(pautaDto.titulo(), pautaDto.descricao());

        pautaRepository.save(novaPauta);

        LOGGER.info("Nova Pauta: {}, criada com sucesso!", novaPauta.getTitulo());

        return novaPauta;
    }

    public List<Pauta> listarTodas() {
        LOGGER.info("Buscando todas as pautas...");
        return pautaRepository.findAll();
    }
}
