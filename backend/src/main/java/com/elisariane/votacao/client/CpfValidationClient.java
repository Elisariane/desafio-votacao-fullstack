package com.elisariane.votacao.client;

public interface CpfValidationClient {

    CpfStatusResponse validarCpf(String cpf);
}
