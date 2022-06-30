package com.uri.gerenciadortcc.gerenciadortccApi.service;

import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.UsuarioObject;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.AlunoLoginDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Aluno;

import java.util.Optional;

public interface AlunoService {

    Aluno salvarAluno(UsuarioObject usuarioObject);

    //retorna true caso email exista
    boolean validarEmail(String email);

    boolean validarCredenciaisADDUsuario(UsuarioObject usuarioObject);

    Long pegaIdAluno(Aluno aluno);

    AlunoLoginDTO Login(String email, String senha);
}
