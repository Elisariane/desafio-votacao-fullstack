package com.elisariane.votacao.model;

import jakarta.persistence.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
public class SessaoVotacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Pauta pauta;

    @Column(nullable = false)
    private ZonedDateTime inicio;

    @Column(nullable = false)
    private ZonedDateTime fim;


    public SessaoVotacao() {
    }

    public SessaoVotacao(Pauta pauta, ZonedDateTime inicio, ZonedDateTime fim) {
        this.pauta = pauta;
        this.inicio = inicio;
        this.fim = fim;
    }

    public boolean isAtiva() {
        ZonedDateTime localDateTimeAgora = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
        return localDateTimeAgora.isEqual(inicio) || localDateTimeAgora.isAfter(inicio)
                && localDateTimeAgora.isBefore(fim);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pauta getPauta() {
        return pauta;
    }

    public void setPauta(Pauta pauta) {
        this.pauta = pauta;
    }

    public ZonedDateTime getInicio() {
        return inicio;
    }

    public void setInicio(ZonedDateTime inicio) {
        this.inicio = inicio;
    }

    public ZonedDateTime getFim() {
        return fim;
    }

    public void setFim(ZonedDateTime fim) {
        this.fim = fim;
    }

}
