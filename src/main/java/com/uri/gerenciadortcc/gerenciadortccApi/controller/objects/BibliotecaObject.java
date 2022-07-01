package com.uri.gerenciadortcc.gerenciadortccApi.controller.objects;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BibliotecaObject {

    private String nomeAluno;

    private String nomeOrientador;

    private String nomeCurso;

    private String tituloTCC;

    private String descricaoTCC;

    private MultipartFile documento;
}
