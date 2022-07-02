package com.uri.gerenciadortcc.gerenciadortccApi.controller;

import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.UsuarioObject;
import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.loginObject;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.AlunoLoginDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.ProfessorCompletoDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.ProfessorDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Doc;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Professor;
import com.uri.gerenciadortcc.gerenciadortccApi.service.DocStorageService;
import com.uri.gerenciadortcc.gerenciadortccApi.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/professor")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private DocStorageService docStorageService;

    @PostMapping("/login")
    public ProfessorCompletoDTO executaLogin(@RequestBody loginObject login){
        ProfessorCompletoDTO professor = professorService.Login(login.getEmail(), login.getSenha());
        return professor;
    }

    @GetMapping("/getProfessor/{professorId}")
    public ProfessorCompletoDTO getProfessor(@PathVariable("professorId") String professorId){
        ProfessorCompletoDTO professor = professorService.getProfessor(Long.valueOf(professorId));
        return professor;
    }

    @PostMapping("/add")
    public ProfessorCompletoDTO add(@RequestBody @Valid UsuarioObject usuarioObject) {
        return professorService.salvarProfessor(usuarioObject);
    }

    @GetMapping("/{cursoId}/getProfessor")
    public ArrayList<ProfessorDTO> getNamoProfessorPorCurso(@PathVariable("cursoId") String cursoId){
        return professorService.getProfessorPorCurso(Long.valueOf(cursoId));
    }

    @PutMapping("/{professorId}/coordenador")
    public ProfessorCompletoDTO transformaProfessorCoordenador(@PathVariable("professorId") String professorId){
        return professorService.transformaProfessorCoordenador(Long.valueOf(professorId));
    }

    @PutMapping("/{professorId}/cursos/{cursoId}")
    public ProfessorCompletoDTO adicionaCurso(@PathVariable("professorId") String professorId, @PathVariable("cursoId") String cursoId){
        return professorService.adicionaCurso(Long.valueOf(professorId), Long.valueOf(cursoId));
    }

    @GetMapping("/{cursoId}/coordenador")
    public ProfessorDTO getNamoProfessorCoordenador(@PathVariable("cursoId") String cursoId){
        return professorService.getProfessorCoordenador(Long.valueOf(cursoId));
    }

    @PutMapping("{professorId}/put")
    public ProfessorCompletoDTO put(@PathVariable("professorId") String professorId, @RequestBody @Valid UsuarioObject usuarioObject) {
        return professorService.atualizaProfessor(Long.valueOf(professorId), usuarioObject);
    }

    @DeleteMapping("{professorId}/delete")
    public String delete(@PathVariable("professorId") String professorId) {
        professorService.deletaProfessor(Long.valueOf(professorId));
        return "Professor deletado com sucesso";
    }

    @PostMapping("/add/{professorId}/document")
    private ResponseEntity<ByteArrayResource> salvaDocumento(@PathVariable("professorId") String professorId, @RequestBody MultipartFile file){
        Doc doc = docStorageService.saveFileProfessor(Long.valueOf(professorId), file);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getDocType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+doc.getDocName()+"\"")
                .body(new ByteArrayResource(doc.getData()));
    }

    @GetMapping("/get/{professorId}/document")
    private ResponseEntity<ByteArrayResource> getDocumento(@PathVariable("professorId") String professorId){
        Doc doc =  docStorageService.getDocumentProfessor(Long.valueOf(professorId));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getDocType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+doc.getDocName()+"\"")
                .body(new ByteArrayResource(doc.getData()));
    }

    @PutMapping("/update/{professorId}/document")
    private ResponseEntity<ByteArrayResource> atualizaDocumento(@PathVariable("professorId") String professorId, @RequestBody MultipartFile file){
        Doc doc = docStorageService.atualizaDocProfessor(Long.valueOf(professorId), file);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getDocType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+doc.getDocName()+"\"")
                .body(new ByteArrayResource(doc.getData()));
    }

    @PutMapping("/delete/{professorId}/document")
    private String deleteDocumento(@PathVariable("professorId") String professorId){
        docStorageService.deleteDocProfessor(Long.valueOf(professorId));
        return "Documento deletado com sucesso";
    }


}
