package com.uri.gerenciadortcc.gerenciadortccApi.model.repository;

import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.DataOrientacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataOrientacaoRepository extends JpaRepository<DataOrientacao, Long> {
}
