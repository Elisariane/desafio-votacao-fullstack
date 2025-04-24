package com.elisariane.votacao.controller;

import com.elisariane.votacao.dto.PautaDto;
import com.elisariane.votacao.model.Pauta;
import com.elisariane.votacao.service.PautaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PautaController.class)
class PautaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PautaService pautaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarPautaComSucesso() throws Exception {
        PautaDto dto = new PautaDto("Título exemplo", "Descrição exemplo");
        Pauta pautaCriada = new Pauta(dto.titulo(), dto.descricao());

        Mockito.when(pautaService.criar(any())).thenReturn(pautaCriada);

        mockMvc.perform(post("/v1/pauta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("Título exemplo"))
                .andExpect(jsonPath("$.descricao").value("Descrição exemplo"));
    }

    @Test
    void deveRetornarErro400QuandoTituloForVazio() throws Exception {
        PautaDto dto = new PautaDto("", "Descrição válida");

        mockMvc.perform(post("/v1/pauta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagens[0]").value("titulo: Por favor defina um título para a pauta"));
    }
}