package com.elisariane.votacao.client;

import com.elisariane.votacao.exception.CpfInvalidoException;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class FakeCpfValidationClient implements CpfValidationClient {

    private final Random random = new Random();

    @Override
    public CpfStatusResponse validarCpf(String cpf) {

        if (cpf == null || cpf.length() != 11) {
            throw new CpfInvalidoException("CPF inválido");
        }

        // Retorna aleatoriamente ABLE_TO_VOTE ou UNABLE_TO_VOTE
        boolean podeVotar = random.nextBoolean();

        return new CpfStatusResponse(podeVotar ? "ABLE_TO_VOTE" : "UNABLE_TO_VOTE");
    }

}
