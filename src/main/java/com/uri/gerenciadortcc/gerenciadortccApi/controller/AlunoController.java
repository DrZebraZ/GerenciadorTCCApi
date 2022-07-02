package com.uri.gerenciadortcc.gerenciadortccApi.controller;

import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.UsuarioObject;
import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.loginObject;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.AlunoDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.AlunoLoginDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.ProfessorCompletoDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.ProfessorDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Aluno;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Doc;
import com.uri.gerenciadortcc.gerenciadortccApi.service.AlunoService;
import com.uri.gerenciadortcc.gerenciadortccApi.service.DocStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
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
    public AlunoLoginDTO add(@RequestBody @Valid UsuarioObject usuarioObject) {
        return alunoService.salvarAluno(usuarioObject);
    }

    @GetMapping("/getAluno/{alunoId}")
    public AlunoLoginDTO getProfessor(@PathVariable("alunoId") String alunoId){
        AlunoLoginDTO professor = alunoService.getAluno(Long.valueOf(alunoId));
        return professor;
    }

    @PutMapping("{alunoId}/put")
    public AlunoLoginDTO put(@PathVariable("alunoId") String alunoId, @RequestBody @Valid UsuarioObject usuarioObject) {
        return alunoService.atualizaAluno(Long.valueOf(alunoId), usuarioObject);
    }

    @DeleteMapping("{alunoId}/delete")
    public String delete(@PathVariable("alunoId") String alunoId) {
        alunoService.deletaAluno(Long.valueOf(alunoId));
        return "Aluno deletado com sucesso";
    }

    @GetMapping("/{cursoId}/getProfessor")
    public ArrayList<AlunoDTO> getNomeAlunoPorCurso(@PathVariable("cursoId") String cursoId){
        return alunoService.getAlunoPorCurso(Long.valueOf(cursoId));
    }

    @PostMapping("/add/{alunoId}/document")
    private ResponseEntity<ByteArrayResource> salvaDocumento(@PathVariable("alunoId") String alunoId, @RequestBody MultipartFile file){
        Doc doc = docStorageService.saveFileAluno(Long.valueOf(alunoId), file);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getDocType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+doc.getDocName()+"\"")
                .body(new ByteArrayResource(doc.getData()));
    }

    @GetMapping("/get/{alunoId}/document")
    private ResponseEntity<ByteArrayResource> getDocumento(@PathVariable("alunoId") String alunoId){
        Doc doc =  docStorageService.getDocumentAluno(Long.valueOf(alunoId));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getDocType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+doc.getDocName()+"\"")
                .body(new ByteArrayResource(doc.getData()));
    }

    @PutMapping("/update/{alunoId}/document")
    private ResponseEntity<ByteArrayResource> atualizaDocumento(@PathVariable("alunoId") String alunoId, @RequestBody MultipartFile file){
        Doc doc = docStorageService.atualizaDocAluno(Long.valueOf(alunoId), file);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getDocType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+doc.getDocName()+"\"")
                .body(new ByteArrayResource(doc.getData()));
    }

    @DeleteMapping("/delete/{alunoId}/document")
    private String deleteDocumento(@PathVariable("alunoId") String alunoId){
        docStorageService.deleteDocAluno(Long.valueOf(alunoId));
        return "Documento deletado com sucesso";
    }
}
