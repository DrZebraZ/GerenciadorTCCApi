package com.uri.gerenciadortcc.gerenciadortccApi.controller;

import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.UsuarioObject;
import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.loginObject;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.ProfessorCompletoDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.ProfessorDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Professor;
import com.uri.gerenciadortcc.gerenciadortccApi.service.DocStorageService;
import com.uri.gerenciadortcc.gerenciadortccApi.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
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
    public String add(@RequestBody @Valid UsuarioObject usuarioObject) {
        professorService.salvarProfessor(usuarioObject);
        return "Professor Adicionado com Sucesso";
    }

    @GetMapping("/{cursoId}/getProfessor")
    public ArrayList<ProfessorDTO> getNamoProfessorPorCurso(@PathVariable("cursoId") String cursoId){
        return professorService.getProfessorPorCurso(Long.valueOf(cursoId));
    }

    @PutMapping("/{professorId}/coordenador")
    public String transformaProfessorCoordenador(@PathVariable("professorId") String professorId){
        professorService.transformaProfessorCoordenador(Long.valueOf(professorId));
        return "Coordenador setado com sucesso";
    }

    @PutMapping("/{professorId}/cursos/{cursoId}")
    public String adicionaCurso(@PathVariable("professorId") String professorId, @PathVariable("cursoId") String cursoId){
        professorService.adicionaCurso(Long.valueOf(professorId), Long.valueOf(cursoId));
        return "Curso adicionado ao Professor";
    }

    @GetMapping("/{cursoId}/coordenador")
    public ProfessorDTO getNamoProfessorCoordenador(@PathVariable("cursoId") String cursoId){
        return professorService.getProfessorCoordenador(Long.valueOf(cursoId));
    }

    @PostMapping("/add/{professorId}/document")
    private String salvaDocumento(@PathVariable("professorId") String professorId, @RequestBody MultipartFile file){
        docStorageService.saveFileProfessor(Long.valueOf(professorId), file);
        return "Documento adicionado com sucesso";
    }

    @GetMapping("/get/{professorId}/document")
    private ByteArrayResource salvaDocumento(@PathVariable("professorId") String professorId){
        return docStorageService.getDocumentProfessor(Long.valueOf(professorId));
    }

    @PutMapping("/update/{professorId}/document")
    private String atualizaDocumento(@PathVariable("professorId") String professorId, @RequestBody MultipartFile file){
        docStorageService.atualizaDocProfessor(Long.valueOf(professorId), file);
        return "Documento atualizado com sucesso";
    }

    @PutMapping("/delete/{professorId}/document")
    private String deleteDocumento(@PathVariable("professorId") String professorId){
        docStorageService.deleteDocProfessor(Long.valueOf(professorId));
        return "Documento deletado com sucesso";
    }


}
