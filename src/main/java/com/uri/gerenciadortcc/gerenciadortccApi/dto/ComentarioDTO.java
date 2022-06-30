package com.uri.gerenciadortcc.gerenciadortccApi.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ComentarioDTO {

    private Long idComentario;
    private String descricao;
    private String comentario;
    private LocalDate dataComentario;
}
