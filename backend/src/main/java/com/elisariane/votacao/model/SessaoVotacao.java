package com.elisariane.votacao.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class SessaoVotacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Pauta pauta;

    @Column(nullable = false)
    private LocalDateTime inicio;

    @Column(nullable = false)
    private LocalDateTime fim;


    public SessaoVotacao() {
    }

    public SessaoVotacao(Pauta pauta, LocalDateTime inicio, LocalDateTime fim) {
        this.pauta = pauta;
        this.inicio = inicio;
        this.fim = fim;
    }

    public boolean isAtiva() {
        LocalDateTime localDateTimeAgora = LocalDateTime.now();
        return localDateTimeAgora.isAfter(inicio) && localDateTimeAgora.isBefore(fim);
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

    public LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public LocalDateTime getFim() {
        return fim;
    }

    public void setFim(LocalDateTime fim) {
        this.fim = fim;
    }

}
