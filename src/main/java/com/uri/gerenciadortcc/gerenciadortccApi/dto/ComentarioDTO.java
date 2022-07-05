package com.uri.gerenciadortcc.gerenciadortccApi.dto;

import com.uri.gerenciadortcc.gerenciadortccApi.model.enums.TipoUsuario;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComentarioDTO {

    private Long idComentario;
    private TipoUsuario autor;
    private String descricao;
    private String comentario;
    private LocalDateTime dataComentario;
}
