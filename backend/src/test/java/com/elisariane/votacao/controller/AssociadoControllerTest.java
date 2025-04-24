package com.elisariane.votacao.controller;

import com.elisariane.votacao.dto.AssociadoDto;
import com.elisariane.votacao.model.Associado;
import com.elisariane.votacao.service.AssociadoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AssociadoController.class)
class AssociadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AssociadoService associadoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCadastrarAssociadoERetornar201() throws Exception {
        AssociadoDto dto = new AssociadoDto("Joana Prado", "62286191018");
        Associado associado = new Associado(dto.nome(), dto.cpf());
        associado.setId(1L);

        when(associadoService.criar(Mockito.any())).thenReturn(associado);

        mockMvc.perform(post("/v1/associados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void deveRetornar400ParaCpfInvalido() throws Exception {
        AssociadoDto dto = new AssociadoDto("Joana Prado", "123");

        mockMvc.perform(post("/v1/associados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagens[0]").exists());
    }

    @Test
    void deveListarAssociadosComSucesso() throws Exception {
        Associado a1 = new Associado("Fulano", "12345678901");
        a1.setId(1L);
        Associado a2 = new Associado("Beltrano", "98765432100");
        a2.setId(2L);

        when(associadoService.listarTodos()).thenReturn(List.of(a1, a2));

        mockMvc.perform(get("/v1/associados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

}