package com.elisariane.votacao.controller;

import com.elisariane.votacao.dto.SessaoVotacaoDto;
import com.elisariane.votacao.exception.PautaJaPossuiSessaoAbertaException;
import com.elisariane.votacao.model.Pauta;
import com.elisariane.votacao.model.SessaoVotacao;
import com.elisariane.votacao.service.SessaoVotacaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SessaoVotacaoController.class)
class SessaoVotacaoControllerTest {

    @Autowired 
    private MockMvc mvc;
    
    @MockitoBean
    private SessaoVotacaoService service;
    
    @Autowired 
    private ObjectMapper objectMapper;

    @Test
    void deveCriarSessaoRetornando201() throws Exception {
        SessaoVotacaoDto dto = new SessaoVotacaoDto(1L, null, null);
        Pauta pauta = new Pauta("Título", "Descrição");
        pauta.setId(1L);

        SessaoVotacao s = new SessaoVotacao(pauta, LocalDateTime.now(), LocalDateTime.now().plusMinutes(1));
        s.setId(123L);

        when(service.abrirSessaoVotacao(Mockito.any())).thenReturn(s);

        mvc.perform(post("/v1/sessoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(123));
    }

    @Test
    void deveRetornar409SeSessaoJaAberta() throws Exception {
        SessaoVotacaoDto dto = new SessaoVotacaoDto(2L, null, null);

        when(service.abrirSessaoVotacao(Mockito.any()))
                .thenThrow(new PautaJaPossuiSessaoAbertaException(2L));

        mvc.perform(post("/v1/sessoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensagens[0]").value("Já existe uma sessão de votação aberta para a pauta com ID: 2"));
    }

    @Test
    void deveRetornar400ParaIdNulo() throws Exception {
        SessaoVotacaoDto dto = new SessaoVotacaoDto(null, null, null);

        mvc.perform(post("/v1/sessoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagens[0]").value("pautaId: É necessário informar o ID da pauta para abrir a sessão"));
    }
}