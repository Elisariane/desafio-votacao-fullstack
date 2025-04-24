package com.elisariane.votacao.model;

import enums.TipoVoto;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private SessaoVotacao sessao;

    @ManyToOne(optional = false)
    private Associado associado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVoto tipoVoto;

    private LocalDateTime dataHora;

    public Voto() {
    }

    public Voto(SessaoVotacao sessao, Associado associado, TipoVoto tipoVoto) {
        this.sessao = sessao;
        this.associado = associado;
        this.tipoVoto = tipoVoto;
        this.dataHora = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SessaoVotacao getSessao() {
        return sessao;
    }

    public void setSessao(SessaoVotacao sessao) {
        this.sessao = sessao;
    }

    public Associado getAssociado() {
        return associado;
    }

    public void setAssociado(Associado associado) {
        this.associado = associado;
    }

    public TipoVoto getTipoVoto() {
        return tipoVoto;
    }

    public void setTipoVoto(TipoVoto tipoVoto) {
        this.tipoVoto = tipoVoto;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}