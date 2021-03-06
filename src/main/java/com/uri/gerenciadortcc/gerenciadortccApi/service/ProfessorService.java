package com.uri.gerenciadortcc.gerenciadortccApi.service;

import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.UsuarioObject;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.ProfessorCompletoDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.ProfessorDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Professor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ProfessorService {

    ProfessorCompletoDTO salvarProfessor(UsuarioObject usuarioObject);

    //retorna true caso email exista
    boolean validarEmail(String email);

    boolean validarCredenciaisADDUsuario(UsuarioObject usuarioObject);

    Long pegaIdProfessor(Professor professor);

    ProfessorCompletoDTO Login(String email, String senha);

    ArrayList<ProfessorDTO> getProfessorPorCurso(Long cursoId);

    ProfessorCompletoDTO transformaProfessorCoordenador(Long professorId);

    ProfessorDTO getProfessorCoordenador(Long cursoId);

    ProfessorCompletoDTO adicionaCurso(Long professorId, Long cursoId);

    ProfessorCompletoDTO getProfessor(Long professorId);

    ProfessorCompletoDTO atualizaProfessor(Long professorId, UsuarioObject usuarioObject);

    void deletaProfessor(Long professorId);
}
