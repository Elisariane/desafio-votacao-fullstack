package com.elisariane.votacao.exception;

public class PautaJaPossuiSessaoAbertaException extends RuntimeException {

    public PautaJaPossuiSessaoAbertaException(Long pautaId) {
        super("Já existe uma sessão de votação aberta para a pauta com ID: " + pautaId);
    }
}