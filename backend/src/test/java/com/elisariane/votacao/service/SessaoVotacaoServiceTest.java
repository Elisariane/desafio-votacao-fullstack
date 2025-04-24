package com.elisariane.votacao.service;

import com.elisariane.votacao.dto.SessaoVotacaoDto;
import com.elisariane.votacao.exception.PautaJaPossuiSessaoAbertaException;
import com.elisariane.votacao.model.Pauta;
import com.elisariane.votacao.model.SessaoVotacao;
import com.elisariane.votacao.repository.PautaRepository;
import com.elisariane.votacao.repository.SessaoVotacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class SessaoVotacaoServiceTest {

    @Mock
    private PautaRepository pautaRepository;

    @Mock
    private SessaoVotacaoRepository sessaoVotacaoRepository;

    @InjectMocks
    private SessaoVotacaoService sessaoService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveAbrirSessaoComValoresPadraoSeNaoFornecido() {
        Pauta pauta = new Pauta("Reforma", "Descrição");
        pauta.setId(1L);

        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));
        when(sessaoVotacaoRepository.existsByPautaAndFimAfter(eq(pauta), any())).thenReturn(false);
        when(sessaoVotacaoRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        SessaoVotacaoDto dto = new SessaoVotacaoDto(1L, null, null);
        SessaoVotacao sessao = sessaoService.abrirSessaoVotacao(dto);

        assertEquals(pauta, sessao.getPauta());
        assertNotNull(sessao.getInicio());
        assertNotNull(sessao.getFim());
        assertTrue(sessao.getFim().isAfter(sessao.getInicio()));
    }

    @Test
    void deveAbrirSessaoComDatasDefinidas() {
        Pauta pauta = new Pauta("Teste", "Sessão customizada");
        pauta.setId(2L);
        LocalDateTime inicio = LocalDateTime.now().plusHours(1);
        LocalDateTime fim = inicio.plusMinutes(10);

        when(pautaRepository.findById(2L)).thenReturn(Optional.of(pauta));
        when(sessaoVotacaoRepository.existsByPautaAndFimAfter(eq(pauta), any())).thenReturn(false);
        when(sessaoVotacaoRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        SessaoVotacaoDto dto = new SessaoVotacaoDto(2L, inicio, fim);
        SessaoVotacao sessao = sessaoService.abrirSessaoVotacao(dto);

        assertEquals(inicio, sessao.getInicio());
        assertEquals(fim, sessao.getFim());
    }

    @Test
    void deveLancarExcecaoSePautaNaoExiste() {
        when(pautaRepository.findById(99L)).thenReturn(Optional.empty());

        SessaoVotacaoDto dto = new SessaoVotacaoDto(99L, null, null);

        assertThrows(EntityNotFoundException.class, () -> sessaoService.abrirSessaoVotacao(dto));
    }

    @Test
    void deveLancarExcecaoSeJaExisteSessaoAberta() {
        Pauta pauta = new Pauta("Já ativa", "Já tem sessão");
        pauta.setId(3L);

        when(pautaRepository.findById(3L)).thenReturn(Optional.of(pauta));
        when(sessaoVotacaoRepository.existsByPautaAndFimAfter(eq(pauta), any())).thenReturn(true);

        SessaoVotacaoDto dto = new SessaoVotacaoDto(3L, null, null);

        assertThrows(PautaJaPossuiSessaoAbertaException.class,
                () -> sessaoService.abrirSessaoVotacao(dto));
    }

}