package com.uri.gerenciadortcc.gerenciadortccApi.dto;

import lombok.Data;

@Data
public class TCCAlunoDTO {

    private Long idTCC;

    private String descricao;

    private Long idProfessor;

    private String nomeProfessor;
}
