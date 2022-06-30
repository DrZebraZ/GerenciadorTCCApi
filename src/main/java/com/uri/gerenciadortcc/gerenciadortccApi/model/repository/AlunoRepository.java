package com.uri.gerenciadortcc.gerenciadortccApi.model.repository;

import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    boolean existsByEmail(String Email);

    Optional<Aluno> findByEmailAndSenha(String Email, String Senha);

    Optional<Aluno> findByEmail(String email);
}
