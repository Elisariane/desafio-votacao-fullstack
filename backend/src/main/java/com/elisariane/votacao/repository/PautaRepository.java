package com.elisariane.votacao.repository;

import com.elisariane.votacao.model.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PautaRepository extends JpaRepository<Pauta, Long> {
}
