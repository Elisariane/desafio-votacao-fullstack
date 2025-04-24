package com.elisariane.votacao.repository;

import com.elisariane.votacao.model.Pauta;
import com.elisariane.votacao.model.SessaoVotacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SessaoVotacaoRepository extends JpaRepository<SessaoVotacao, Long> {

    @Query("""
    select case when count(s)>0 then true else false end
      from SessaoVotacao s
     where s.pauta = :pauta
       and s.fim > CURRENT_TIMESTAMP
    """)
    boolean existsActiveSessionByPauta(Pauta pauta);
}
