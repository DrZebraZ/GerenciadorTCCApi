package com.uri.gerenciadortcc.gerenciadortccApi.controller.objects;

import lombok.Data;

@Data
public class TCCObject {

    private String titulo;
    private String descricao;
    private Long professorId;
    private Long alunoId;
}
