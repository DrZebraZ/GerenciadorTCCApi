package com.uri.gerenciadortcc.gerenciadortccApi.model.repository;

import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    boolean existsByEmail(String Email);

    Optional<Aluno> findByEmailAndSenha(String Email, String Senha);

    Optional<Aluno> findByEmail(String email);

    @Query(value = "SELECT * FROM ALUNO WHERE curso_id = :cursoId", nativeQuery = true)
    List<Aluno> findyByIdCurso(Long cursoId);
}
