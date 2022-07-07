package com.uri.gerenciadortcc.gerenciadortccApi.controller;

import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.TCCObject;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.TCCDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Doc;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.TCC;
import com.uri.gerenciadortcc.gerenciadortccApi.service.DocStorageService;
import com.uri.gerenciadortcc.gerenciadortccApi.service.TCCService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/tcc")
@Slf4j
public class TCCController {

    @Autowired
    private TCCService tccService;

    @Autowired
    private DocStorageService docStorageService;

    @PostMapping("/add")
    private TCCDTO addTCC(@RequestBody TCCObject tccObject){
        TCCDTO dto = tccService.salvaTCC(tccObject);
        return dto;
    }

    @GetMapping("/{tccId}/getTCC")
    private TCCDTO getTCC(@PathVariable("tccId")String tccId){
        TCCDTO tcc = tccService.getTCC(Long.valueOf(tccId));
        return tcc;
    }

    @PutMapping("/{tccId}/update")
    private TCCDTO putTCC(@PathVariable("tccId")String tccId, @RequestBody TCCObject tccObject) {
        return tccService.atualizaTCC(Long.valueOf(tccId), tccObject);
    }

    @DeleteMapping("/{tccId}/delete")
    private String deletaTCC(@PathVariable("tccId")String tccId){
        tccService.deleteTCC(Long.valueOf(tccId));
        return "TCC deletado com Sucesso";
    }

    @PostMapping("/{tccId}/biblioteca")
    private String colocaTCCNaBiblioteca(@PathVariable("tccId")String tccId){
        tccService.colocaNaBiblioteca(Long.valueOf(tccId));
        return "TCC adicionado para a biblioteca";
    }

    @PostMapping("/add/{tccId}/document")
    private ResponseEntity<ByteArrayResource> salvaDocumento(@PathVariable("tccId") String tccId, @RequestBody MultipartFile file){
        Doc doc = docStorageService.saveFile(Long.valueOf(tccId), file);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getDocType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+doc.getDocName()+"\"")
                .body(new ByteArrayResource(doc.getData()));
    }

    @GetMapping("/get/{tccId}/document")
    private ResponseEntity<InputStreamResource> getDocumento(@PathVariable("tccId") String tccId){
        Doc doc = docStorageService.getDocument(Long.valueOf(tccId));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getDocType()))
                .body(new InputStreamResource(new ByteArrayInputStream(doc.getData())));
    }

    @PutMapping("/update/{tccId}/document")
    private ResponseEntity<ByteArrayResource> atualizaDocumento(@PathVariable("tccId") String tccId, @RequestBody MultipartFile file){
        Doc doc = docStorageService.atualiza(Long.valueOf(tccId), file);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getDocType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+doc.getDocName()+"\"")
                .body(new ByteArrayResource(doc.getData()));
    }

    @DeleteMapping("/delete/{tccId}/document")
    private String deleteDocumento(@PathVariable("tccId") String tccId){
        docStorageService.delete(Long.valueOf(tccId));
        return "Documento deletado com sucesso";
    }


}
