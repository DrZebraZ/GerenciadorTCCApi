package com.uri.gerenciadortcc.gerenciadortccApi.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ProfessorCompletoDTO {

    private Long id;

    private String nome;

    private String cpf;

    private LocalDate datanasc;

    private String email;

    private Boolean coordenador;

    private List<TCCProfessorDTO> tccs;

    private List<CursoReturnDTO> cursos;

}
