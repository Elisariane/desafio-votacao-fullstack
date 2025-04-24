package com.elisariane.votacao.client;

public record CpfStatusResponse(String status) {

    public boolean isAbleToVote() {
        return "ABLE_TO_VOTE".equalsIgnoreCase(status);
    }

}