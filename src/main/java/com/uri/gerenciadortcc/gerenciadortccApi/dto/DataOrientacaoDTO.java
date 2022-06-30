package com.uri.gerenciadortcc.gerenciadortccApi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DataOrientacaoDTO {

    private Long idDataOrientacao;
    private LocalDateTime data;
}
