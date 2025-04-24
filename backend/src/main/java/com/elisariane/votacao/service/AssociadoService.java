package com.elisariane.votacao.service;

import com.elisariane.votacao.dto.AssociadoDto;
import com.elisariane.votacao.model.Associado;
import com.elisariane.votacao.repository.AssociadoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssociadoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssociadoService.class);
    private final AssociadoRepository associadoRepository;

    public AssociadoService(AssociadoRepository associadoRepository) {
        this.associadoRepository = associadoRepository;
    }

    public Associado criar(AssociadoDto dto) {
        LOGGER.info("Criando associado com CPF {}", dto.cpf());

        if (associadoRepository.existsByCpf(dto.cpf())) {
            LOGGER.warn("Associado com CPF {} já cadastrado", dto.cpf());
            throw new IllegalArgumentException("CPF já cadastrado.");
        }

        Associado associado = associadoRepository.save(new Associado(dto.nome(), dto.cpf()));

        LOGGER.info("Associado criado com sucesso");
        return associado;
    }

    public List<Associado> listarTodos() {
        return associadoRepository.findAll();
    }

}