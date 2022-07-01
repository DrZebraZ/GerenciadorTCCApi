package com.uri.gerenciadortcc.gerenciadortccApi.model.repository;

import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Biblioteca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BibliotecaRepository extends JpaRepository<Biblioteca, Long> {

    @Query(value = "SELECT * FROM BIBLIOTECA WHERE NOME_CURSO LIKE %:nomeCurso%", nativeQuery = true)
    List<Biblioteca> findByCursoNome(@Param("nomeCurso") String nomeCurso);

    @Query(value = "SELECT * FROM BIBLIOTECA WHERE TITULO_TCC LIKE %:tituloTCC%", nativeQuery = true)
    List<Biblioteca> findByTituloTCC(@Param("tituloTCC") String tituloTCC);
}
