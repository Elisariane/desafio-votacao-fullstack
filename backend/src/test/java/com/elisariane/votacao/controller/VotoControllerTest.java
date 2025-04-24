package com.elisariane.votacao.controller;

import com.elisariane.votacao.dto.VotoDto;
import com.elisariane.votacao.model.Voto;
import com.elisariane.votacao.service.VotoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import enums.TipoVoto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VotoController.class)
class VotoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    VotoService votoService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void deveRetornar201QuandoVotoForRegistrado() throws Exception {
        VotoDto dto = new VotoDto(1L, 1L, TipoVoto.SIM);

        Voto voto = new Voto(null, null, TipoVoto.SIM);
        voto.setId(1L);

        when(votoService.votar(any())).thenReturn(voto);

        mockMvc.perform(post("/v1/votos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void deveRetornar400ParaVotoDuplicadoOuCpfInvalido() throws Exception {
        VotoDto dto = new VotoDto(1L, 1L, TipoVoto.SIM);

        when(votoService.votar(any())).thenThrow(new IllegalArgumentException("Associado já votou nessa pauta"));

        mockMvc.perform(post("/v1/votos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}