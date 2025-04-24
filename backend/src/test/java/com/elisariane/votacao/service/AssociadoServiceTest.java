package com.elisariane.votacao.service;

import com.elisariane.votacao.dto.AssociadoDto;
import com.elisariane.votacao.model.Associado;
import com.elisariane.votacao.repository.AssociadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AssociadoServiceTest {

    @Mock
    private AssociadoRepository associadoRepository;

    @InjectMocks
    private AssociadoService associadoService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarAssociadoComSucesso() {
        AssociadoDto dto = new AssociadoDto("Joana Prado", "12345678901");

        when(associadoRepository.existsByCpf(dto.cpf())).thenReturn(false);
        when(associadoRepository.save(any())).thenAnswer(invocation -> {
            Associado associado = invocation.getArgument(0);
            associado.setId(1L);
            return associado;
        });

        Associado salvo = associadoService.criar(dto);

        assertEquals(dto.nome(), salvo.getNome());
        assertEquals(dto.cpf(), salvo.getCpf());
        assertNotNull(salvo.getId());
    }

    @Test
    void deveLancarExcecaoSeCpfJaExistir() {
        AssociadoDto dto = new AssociadoDto("Carlos Silva", "11111111111");

        when(associadoRepository.existsByCpf(dto.cpf())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> associadoService.criar(dto));
    }

    @Test
    void deveListarTodosOsAssociados() {
        List<Associado> mockList = List.of(
                new Associado("Fulano", "12345678901"),
                new Associado("Ciclano", "98765432100")
        );

        when(associadoRepository.findAll()).thenReturn(mockList);

        List<Associado> lista = associadoService.listarTodos();

        assertEquals(2, lista.size());
    }

}