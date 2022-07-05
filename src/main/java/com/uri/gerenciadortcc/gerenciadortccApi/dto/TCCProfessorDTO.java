package com.uri.gerenciadortcc.gerenciadortccApi.dto;

import lombok.Data;

@Data
public class TCCProfessorDTO {

    private Long idTCC;

    private String titulo;

    private String descricao;

    private Long idAluno;

    private String nomeAluno;
}
