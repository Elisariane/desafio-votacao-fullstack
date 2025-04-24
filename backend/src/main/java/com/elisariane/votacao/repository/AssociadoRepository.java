package com.elisariane.votacao.repository;

import com.elisariane.votacao.model.Associado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssociadoRepository extends JpaRepository<Associado, Long> {

    boolean existsByCpf(String cpf);
    Optional<Associado> findByCpf(String cpf);

}
