package com.uri.gerenciadortcc.gerenciadortccApi.controller;

import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.UsuarioObject;
import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.loginObject;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.AlunoLoginDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Aluno;
import com.uri.gerenciadortcc.gerenciadortccApi.service.AlunoService;
import com.uri.gerenciadortcc.gerenciadortccApi.service.DocStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private DocStorageService docStorageService;

    @PostMapping("/login")
    public AlunoLoginDTO executaLogin(@RequestBody loginObject login){
        return alunoService.Login(login.getEmail(), login.getSenha());
    }

    @PostMapping("/add")
    public String add(@RequestBody @Valid UsuarioObject usuarioObject) {
        alunoService.salvarAluno(usuarioObject);
        return "Aluno Adicionado com Sucesso";
    }

    @PostMapping("/add/{alunoId}/document")
    private String salvaDocumento(@PathVariable("alunoId") String alunoId, @RequestBody MultipartFile file){
        docStorageService.saveFileAluno(Long.valueOf(alunoId), file);
        return "Documento adicionado com sucesso";
    }

    @GetMapping("/get/{alunoId}/document")
    private ByteArrayResource salvaDocumento(@PathVariable("alunoId") String alunoId){
        return docStorageService.getDocumentAluno(Long.valueOf(alunoId));
    }

    @PutMapping("/update/{alunoId}/document")
    private String atualizaDocumento(@PathVariable("alunoId") String alunoId, @RequestBody MultipartFile file){
        docStorageService.atualizaDocAluno(Long.valueOf(alunoId), file);
        return "Documento atualizado com sucesso";
    }

    @DeleteMapping("/delete/{alunoId}/document")
    private String deleteDocumento(@PathVariable("alunoId") String alunoId){
        docStorageService.deleteDocAluno(Long.valueOf(alunoId));
        return "Documento deletado com sucesso";
    }
}
