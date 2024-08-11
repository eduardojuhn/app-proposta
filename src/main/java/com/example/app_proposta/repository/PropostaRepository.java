package com.example.app_proposta.repository;

import com.example.app_proposta.entity.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {

    List<Proposta> findAllByIntegradaIsFalse();
}
