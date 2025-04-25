package com.elisariane.votacao.service;

import com.elisariane.votacao.dto.ResultadoVotacaoDto;
import com.elisariane.votacao.model.Pauta;
import com.elisariane.votacao.repository.PautaRepository;
import com.elisariane.votacao.repository.VotoRepository;
import enums.ResultadoVotacao;
import enums.TipoVoto;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResultadoVotacaoServiceTest {

    @Mock
    PautaRepository pautaRepo;
    
    @Mock
    VotoRepository votoRepo;
    
    @InjectMocks
    ResultadoVotacaoService service;

    @Test
    void apuraAprovadoQuandoSimMaiorQueNao() {
        when(pautaRepo.findById(1L)).thenReturn(Optional.of(new Pauta()));
        when(votoRepo.countBySessao_Pauta_IdAndTipoVoto(1L, TipoVoto.SIM)).thenReturn(5L);
        when(votoRepo.countBySessao_Pauta_IdAndTipoVoto(1L, TipoVoto.NAO)).thenReturn(2L);

        ResultadoVotacaoDto resultado = service.apurarResultado(1L);

        assertEquals(5, resultado.totalSim());
        assertEquals(2, resultado.totalNao());
        assertEquals(ResultadoVotacao.APROVADO, resultado.resultadoVotacao());
    }

    @Test
    void apuraRejeitadoQuandoNaoMaiorQueSim() {
        when(pautaRepo.findById(2L)).thenReturn(Optional.of(new Pauta()));
        when(votoRepo.countBySessao_Pauta_IdAndTipoVoto(2L, TipoVoto.SIM)).thenReturn(1L);
        when(votoRepo.countBySessao_Pauta_IdAndTipoVoto(2L, TipoVoto.NAO)).thenReturn(3L);

        ResultadoVotacaoDto resultado = service.apurarResultado(2L);

        assertEquals(ResultadoVotacao.REJEITADO, resultado.resultadoVotacao());
    }

    @Test
    void apuraEmpateQuandoSimIgualNao() {
        when(pautaRepo.findById(3L)).thenReturn(Optional.of(new Pauta()));
        when(votoRepo.countBySessao_Pauta_IdAndTipoVoto(3L, TipoVoto.SIM)).thenReturn(4L);
        when(votoRepo.countBySessao_Pauta_IdAndTipoVoto(3L, TipoVoto.NAO)).thenReturn(4L);

        ResultadoVotacaoDto resultado = service.apurarResultado(3L);

        assertEquals(ResultadoVotacao.EMPATE, resultado.resultadoVotacao());
    }

    @Test
    void erro404SePautaNaoExistir() {
        when(pautaRepo.findById(9L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.apurarResultado(9L));
    }
}