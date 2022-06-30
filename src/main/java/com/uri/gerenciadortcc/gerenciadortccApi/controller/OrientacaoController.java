package com.uri.gerenciadortcc.gerenciadortccApi.controller;

import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.ComentarioObject;
import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.OrientacaoObject;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.ComentariosDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.OrientacaoDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.service.OrientacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/orientacao")
public class OrientacaoController {

    @Autowired
    private OrientacaoService orientacaoService;

    @PostMapping("/add")
    public String add(@RequestBody OrientacaoObject orientacao) {
        orientacaoService.addOrientacao(orientacao);
        return "Orientacao Adicionada com Sucesso";
    }

    @GetMapping("/getOrientacao/{orientacaoId}")
    public OrientacaoDTO getOrientacao(@PathVariable("orientacaoId") String orientacaoId){
        OrientacaoDTO orientacao = orientacaoService.getOrientacao(Long.valueOf(orientacaoId));
        return orientacao;
    }

    @GetMapping("/getOrientacao/{alunoId}")
    public OrientacaoDTO getOrientacaoPorAluno(@PathVariable("alunoId") String alunoId){
        OrientacaoDTO orientacao = orientacaoService.getOrientacaoPorAluno(Long.valueOf(alunoId));
        return orientacao;
    }

    @GetMapping("/getOrientacao/{professorId}")
    public List<OrientacaoDTO> getOrientacaoPorProfessor(@PathVariable("professorId") String professorId){
        List<OrientacaoDTO> orientacoes = orientacaoService.getOrientacaoPorProfessor(Long.valueOf(professorId));
        return orientacoes;
    }

    @PostMapping("/add/{orientacaoId}/comentario")
    public String addComentario(@PathVariable("orientacaoId") String orientacaoId, @RequestBody ComentarioObject comentario){
        orientacaoService.addComentario(Long.valueOf(orientacaoId), comentario);
        return "Comentario adicionado com sucesso";
    }

    @GetMapping("/get/{orientacaoId}/comentario")
    public ComentariosDTO getComentarios(@PathVariable("orientacaoId") String orientacaoId){
        ComentariosDTO comentarios = orientacaoService.getComentarios(Long.valueOf(orientacaoId));
        return comentarios;
    }

    @PutMapping("/put/{comentarioId}/comentario")
    public String atualizaComentario(@PathVariable("comentarioId") String comentarioId, @RequestBody ComentarioObject comentario){
        orientacaoService.atualizaComentario(Long.valueOf(comentarioId), comentario);
        return "Comentario atualizado com sucesso";
    }

    @DeleteMapping("/delete/{comentarioId}/comentario")
    public String atualizaComentario(@PathVariable("comentarioId") String comentarioId){
        orientacaoService.deletaComentario(Long.valueOf(comentarioId));
        return "Comentario deletado com sucesso";
    }

    @PutMapping("/put/{orientacaoId}/marcaData")
    public String marcaDataOrientacao(@PathVariable("orientacaoId") String orientacaoId, @RequestBody LocalDateTime dataOrientacao){
        orientacaoService.marcaOrientacao(Long.valueOf(orientacaoId), dataOrientacao);
        return "Data marcada com sucesso";
    }

    @PutMapping("/put/{dataOrientacaoId}/atualizaData")
    public String atualizaDataOrientacao(@PathVariable("dataOrientacaoId") String dataOrientacaoId, @RequestBody LocalDateTime dataOrientacao){
        orientacaoService.atualizaDataOrientacao(Long.valueOf(dataOrientacaoId), dataOrientacao);
        return "Data atualizada com sucesso";
    }

    @DeleteMapping("/delete/{dataOrientacaoId}")
    public String deletaDataOrientacao(@PathVariable("dataOrientacaoId") String dataOrientacaoId){
        orientacaoService.deletaDataOrientacao(Long.valueOf(dataOrientacaoId));
        return "Data deletada com sucesso";
    }

}
