package com.elisariane.votacao.service;

import com.elisariane.votacao.dto.PautaDto;
import com.elisariane.votacao.model.Pauta;
import com.elisariane.votacao.repository.PautaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class PautaServiceTest {

    @Mock
    private PautaRepository pautaRepository;

    @InjectMocks
    private PautaService pautaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void tentaCriarUmaNovaPauta() {
        PautaDto dto = new PautaDto("Título da Pauta", "Descrição");
        Pauta pautaSalva = new Pauta("Título da Pauta", "Descrição");

        Mockito.when(pautaRepository.save(any(Pauta.class))).thenReturn(pautaSalva);

        Pauta resultado = pautaService.criar(dto);

        assertNotNull(resultado);
        assertEquals("Título da Pauta", resultado.getTitulo());
        assertEquals("Descrição", resultado.getDescricao());
        verify(pautaRepository, times(1)).save(any(Pauta.class));
    }
}