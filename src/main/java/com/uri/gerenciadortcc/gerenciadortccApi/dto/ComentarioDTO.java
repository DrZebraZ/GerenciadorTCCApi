package com.uri.gerenciadortcc.gerenciadortccApi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComentarioDTO {

    private Long idComentario;
    private String descricao;
    private String comentario;
    private LocalDateTime dataComentario;
}
