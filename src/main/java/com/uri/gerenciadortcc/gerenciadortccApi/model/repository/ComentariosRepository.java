package com.uri.gerenciadortcc.gerenciadortccApi.model.repository;

import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Comentarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentariosRepository extends JpaRepository<Comentarios, Long> {
}
