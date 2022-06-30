package com.uri.gerenciadortcc.gerenciadortccApi.model.repository;

import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.TCC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TCCRepository extends JpaRepository<TCC, Long> {
}
