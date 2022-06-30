package com.uri.gerenciadortcc.gerenciadortccApi.dto;

import lombok.Data;

@Data
public class TCCDTO {

    private Long id;

    private String titulo;

    private String descricao;

    private ProfessorDTO professor;

    private AlunoDTO alunoDTO;

    private Long docId;
}
