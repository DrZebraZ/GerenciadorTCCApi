package com.uri.gerenciadortcc.gerenciadortccApi.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AlunoLoginDTO {

    private Long id;

    private String nome;

    private String cpf;

    private LocalDate datanasc;

    private String email;

    private TCCAlunoDTO tccAlunoDTO;
    
    private Long idCurso;
    
    private String nomeCurso;

}
