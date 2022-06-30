package com.uri.gerenciadortcc.gerenciadortccApi.service;

import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Doc;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

public interface DocStorageService {

    public Doc saveFile(Long tccId, MultipartFile file);

    public Doc atualiza(Long tccId, MultipartFile file);

    public Doc getDocument(Long tccId);

    public Doc delete(Long tccId);

    public Doc saveFileProfessor(Long professorId, MultipartFile file);

    public Doc atualizaDocProfessor(Long professorId, MultipartFile file);

    public ByteArrayResource getDocumentProfessor(Long professorId);

    public Doc deleteDocProfessor(Long professorId);

    public Doc saveFileAluno(Long alunoId, MultipartFile file);

    public Doc atualizaDocAluno(Long alunoId, MultipartFile file);

    public ByteArrayResource getDocumentAluno(Long alunoId);

    public Doc deleteDocAluno(Long alunoId);
}
