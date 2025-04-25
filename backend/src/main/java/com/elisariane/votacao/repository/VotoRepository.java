package com.elisariane.votacao.repository;

import com.elisariane.votacao.model.Associado;
import com.elisariane.votacao.model.SessaoVotacao;
import com.elisariane.votacao.model.Voto;
import enums.TipoVoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotoRepository extends JpaRepository<Voto, Long> {

    boolean existsBySessaoAndAssociado(SessaoVotacao sessao, Associado associado);

    long countBySessao_Pauta_IdAndTipoVoto(Long pautaId, TipoVoto voto);
}
