package com.uri.gerenciadortcc.gerenciadortccApi.model.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Notificacao;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long>{
	
}
