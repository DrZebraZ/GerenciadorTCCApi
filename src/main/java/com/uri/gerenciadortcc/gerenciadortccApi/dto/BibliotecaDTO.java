package com.uri.gerenciadortcc.gerenciadortccApi.dto;

import lombok.Data;

@Data
public class BibliotecaDTO {

    private Long id;

    private String nomeAluno;

    private String nomeOrientador;

    private String nomeCurso;

    private String tituloTCC;

    private String descricaoTCC;

    private Long idDoc;
}
