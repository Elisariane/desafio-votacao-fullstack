package com.elisariane.votacao.controller;

import com.elisariane.votacao.dto.ResultadoVotacaoDto;
import com.elisariane.votacao.service.ResultadoVotacaoService;
import enums.ResultadoVotacao;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ResultadoVotacaoController.class)
class ResultadoVotacaoControllerTest {

    @Autowired
    MockMvc mvc;
    
    @MockitoBean
    ResultadoVotacaoService service;

    @Test
    void deveRetornar200ComResultado() throws Exception {
        ResultadoVotacaoDto dto = new ResultadoVotacaoDto(1L, 3, 1, ResultadoVotacao.APROVADO);
        when(service.apurarResultado(1L)).thenReturn(dto);

        mvc.perform(get("/v1/resultados/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalSim").value(3))
                .andExpect(jsonPath("$.resultadoVotacao").value("APROVADO"));
    }

    @Test
    void deveRetornar404SePautaNaoExistir() throws Exception {
        when(service.apurarResultado(5L)).thenThrow(new EntityNotFoundException("Pauta não encontrada"));

        mvc.perform(get("/v1/resultados/5"))
                .andExpect(status().isNotFound());
    }
    
}