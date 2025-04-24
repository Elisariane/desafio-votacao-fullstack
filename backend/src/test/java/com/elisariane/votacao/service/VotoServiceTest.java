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
import enums.TipoVoto;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VotoServiceTest {

    @Mock
    SessaoVotacaoRepository sessaoRepository;

    @Mock
    AssociadoRepository associadoRepository;

    @Mock
    VotoRepository votoRepository;

    @Mock
    CpfValidationClient cpfValidationClient;

    @InjectMocks
    VotoService votoService;

    @Test
    void deveRegistrarVotoComSucesso() {
        VotoDto dto = new VotoDto(1L, 1L, TipoVoto.SIM);
        SessaoVotacao sessao = mock(SessaoVotacao.class);
        when(sessao.isAtiva()).thenReturn(true);

        Associado associado = new Associado("João", "23632445834");
        associado.setId(1L);

        when(sessaoRepository.findById(1L)).thenReturn(Optional.of(sessao));
        when(associadoRepository.findById(1L)).thenReturn(Optional.of(associado));
        when(cpfValidationClient.validarCpf("23632445834")).thenReturn(new CpfStatusResponse("ABLE_TO_VOTE"));
        when(votoRepository.existsBySessaoAndAssociado(sessao, associado)).thenReturn(false);
        when(votoRepository.save(any(Voto.class))).thenAnswer(inv -> inv.getArgument(0));

        Voto voto = votoService.votar(dto);

        assertEquals(TipoVoto.SIM, voto.getTipoVoto());
        assertEquals(associado, voto.getAssociado());
        assertEquals(sessao, voto.getSessao());
    }

    @Test
    void deveLancarExcecaoQuandoSessaoNaoExiste() {
        when(sessaoRepository.findById(1L)).thenReturn(Optional.empty());

        VotoDto dto = new VotoDto(1L, 1L, TipoVoto.SIM);
        assertThrows(EntityNotFoundException.class, () -> votoService.votar(dto));
    }

    @Test
    void deveLancarExcecaoQuandoSessaoNaoEstaAtiva() {
        SessaoVotacao sessao = mock(SessaoVotacao.class);
        when(sessao.isAtiva()).thenReturn(false);
        when(sessaoRepository.findById(1L)).thenReturn(Optional.of(sessao));

        VotoDto dto = new VotoDto(1L, 1L, TipoVoto.SIM);
        assertThrows(IllegalStateException.class, () -> votoService.votar(dto));
    }

    @Test
    void deveLancarExcecaoQuandoAssociadoNaoExiste() {
        SessaoVotacao sessao = mock(SessaoVotacao.class);
        when(sessao.isAtiva()).thenReturn(true);
        when(sessaoRepository.findById(1L)).thenReturn(Optional.of(sessao));
        when(associadoRepository.findById(1L)).thenReturn(Optional.empty());

        VotoDto dto = new VotoDto(1L, 1L, TipoVoto.SIM);
        assertThrows(EntityNotFoundException.class, () -> votoService.votar(dto));
    }

    @Test
    void deveLancarExcecaoQuandoCpfNaoAutorizado() {
        SessaoVotacao sessao = mock(SessaoVotacao.class);
        when(sessao.isAtiva()).thenReturn(true);

        Associado associado = new Associado("João", "12345678900");
        associado.setId(1L);

        when(sessaoRepository.findById(1L)).thenReturn(Optional.of(sessao));
        when(associadoRepository.findById(1L)).thenReturn(Optional.of(associado));
        when(cpfValidationClient.validarCpf("12345678900")).thenReturn(new CpfStatusResponse("UNABLE_TO_VOTE"));

        VotoDto dto = new VotoDto(1L, 1L, TipoVoto.SIM);
        assertThrows(IllegalStateException.class, () -> votoService.votar(dto));
    }

    @Test
    void deveLancarExcecaoQuandoAssociadoJaVotou() {
        SessaoVotacao sessao = mock(SessaoVotacao.class);
        when(sessao.isAtiva()).thenReturn(true);

        Associado associado = new Associado("João", "12345678900");
        associado.setId(1L);

        when(sessaoRepository.findById(1L)).thenReturn(Optional.of(sessao));
        when(associadoRepository.findById(1L)).thenReturn(Optional.of(associado));
        when(cpfValidationClient.validarCpf("12345678900")).thenReturn(new CpfStatusResponse("ABLE_TO_VOTE"));
        when(votoRepository.existsBySessaoAndAssociado(sessao, associado)).thenReturn(true);

        VotoDto dto = new VotoDto(1L, 1L, TipoVoto.SIM);
        assertThrows(IllegalArgumentException.class, () -> votoService.votar(dto));
    }
}
