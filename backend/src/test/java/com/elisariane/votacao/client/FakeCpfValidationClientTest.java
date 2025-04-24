package com.elisariane.votacao.client;

import com.elisariane.votacao.exception.CpfInvalidoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FakeCpfValidationClientTest {

    private final FakeCpfValidationClient client = new FakeCpfValidationClient();

    @Test
    void deveRetornarAbleOuUnableToVote() {
        String cpfValido = "62286191018";
        var response = client.validarCpf(cpfValido);
        assertTrue(response.status().equals("ABLE_TO_VOTE") || response.status().equals("UNABLE_TO_VOTE"));
    }

    @Test
    void deveLancarExcecaoParaCpfInvalido() {
        String cpfInvalido = "123123321";
        assertThrows(CpfInvalidoException.class, () -> client.validarCpf(cpfInvalido));
    }
}