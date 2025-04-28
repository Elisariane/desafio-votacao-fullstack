package com.elisariane.votacao.repository;

import com.elisariane.votacao.model.Pauta;
import com.elisariane.votacao.model.SessaoVotacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SessaoVotacaoRepository extends JpaRepository<SessaoVotacao, Long> {

    @Query("""
        SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END
        FROM SessaoVotacao s
        WHERE s.pauta = :pauta
          AND s.inicio <= CURRENT_TIMESTAMP
          AND s.fim    > CURRENT_TIMESTAMP
    """)
    boolean existsActiveSessionByPauta(Pauta pauta);
}
