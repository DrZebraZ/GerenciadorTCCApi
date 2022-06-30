package com.uri.gerenciadortcc.gerenciadortccApi.model.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Curso;
@Repository
public interface CursoRepository extends JpaRepository<Curso, Long>{
	
	@Query(value = "SELECT * FROM `curso`", nativeQuery = true)
	ArrayList<Curso> retornaCursos();
	
}
