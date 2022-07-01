package com.uri.gerenciadortcc.gerenciadortccApi.service;

import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Doc;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Orientacao;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public interface DocStorageService {

    public Doc saveFile(Long tccId, MultipartFile file);

    public Doc atualiza(Long tccId, MultipartFile file);

    public Doc getDocument(Long tccId);

    public Doc delete(Long tccId);

    public Doc saveFileProfessor(Long professorId, MultipartFile file);

    public Doc atualizaDocProfessor(Long professorId, MultipartFile file);

    public Doc getDocumentProfessor(Long professorId);

    public Doc deleteDocProfessor(Long professorId);

    public Doc saveFileAluno(Long alunoId, MultipartFile file);

    public Doc atualizaDocAluno(Long alunoId, MultipartFile file);

    public Doc getDocumentAluno(Long alunoId);

    public Doc deleteDocAluno(Long alunoId);

    public Doc saveFileBiblioteca(Long bibliotecaId, MultipartFile file);

    public Doc atualizaDocBiblioteca(Long bibliotecaId, MultipartFile file);

    public Doc getDocumentBiblioteca(Long bibliotecaId);

    public Doc deleteDocBiblioteca(Long bibliotecaId);

    public ByteArrayInputStream getRelatorioOrientacao(Orientacao orientacao) throws IOException;
}
