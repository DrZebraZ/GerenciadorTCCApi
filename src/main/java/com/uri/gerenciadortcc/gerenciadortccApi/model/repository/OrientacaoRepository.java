package com.uri.gerenciadortcc.gerenciadortccApi.model.repository;

import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Orientacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrientacaoRepository extends JpaRepository<Orientacao, Long> {
}
