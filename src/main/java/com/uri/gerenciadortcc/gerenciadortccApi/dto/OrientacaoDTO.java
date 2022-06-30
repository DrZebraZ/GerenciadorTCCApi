package com.uri.gerenciadortcc.gerenciadortccApi.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrientacaoDTO {

    private Long orientacacaoId;
    private Long alunoId;
    private String tituloTCC;
    private String nomeAluno;
    private Long professorId;
    private String nomeProfessor;
    private List<DataOrientacaoDTO> datasOrientacoes;
    private ComentariosDTO comentarios;
}
