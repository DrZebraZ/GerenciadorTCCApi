package com.uri.gerenciadortcc.gerenciadortccApi.service;

import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.ComentarioObject;
import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.DataOrientacaoObject;
import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.OrientacaoObject;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.ComentariosDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.OrientacaoDTO;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface OrientacaoService {

    public OrientacaoDTO addOrientacao(OrientacaoObject orientacaoObject);

    public OrientacaoDTO addComentario(Long orientacaoId, ComentarioObject comentarioObject);

    public ComentariosDTO getComentarios(Long orientacaoId);

    public OrientacaoDTO getOrientacao(Long orientacaoId);

    public OrientacaoDTO getOrientacaoPorAluno(Long alunoId);

    public List<OrientacaoDTO> getOrientacaoPorProfessor(Long professorId);

    public OrientacaoDTO atualizaComentario(Long comentarioId, ComentarioObject comentarioObject);

    public void deletaComentario(Long comentarioId);

    public OrientacaoDTO marcaOrientacao(Long valueOf, DataOrientacaoObject dataOrientacao);

    OrientacaoDTO atualizaDataOrientacao(Long dataOrientacaoId, DataOrientacaoObject dataOrientacao);

    void deletaDataOrientacao(Long dataOrientacaoId);

    public ByteArrayInputStream getRelatorioOrientacao(Long orientacaoId) throws IOException;
}
