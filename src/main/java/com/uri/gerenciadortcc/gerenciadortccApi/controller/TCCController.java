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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private String addTCC(@RequestBody TCCObject tccObject){
        tccService.salvaTCC(tccObject);
        return "TCC adicionado com sucesso";
    }

    @GetMapping("/{tccId}/getTCC")
    private TCCDTO getTCC(@PathVariable("tccId")String tccId){
        TCCDTO tcc = tccService.getTCC(Long.valueOf(tccId));
        return tcc;
    }

    @PutMapping("/{tccId}/update")
    private String putTCC(@PathVariable("tccId")String tccId, @RequestBody TCCObject tccObject) {
        tccService.atualizaTCC(Long.valueOf(tccId), tccObject);
        return "TCC atualizado com Sucesso";
    }

    @DeleteMapping("/{tccId}/delete")
    private String deletaTCC(@PathVariable("tccId")String tccId){
        tccService.deleteTCC(Long.valueOf(tccId));
        return "TCC deletado com Sucesso";
    }

    @PostMapping("/add/{tccId}/document")
    private String salvaDocumento(@PathVariable("tccId") String tccId, @RequestBody MultipartFile file){
        docStorageService.saveFile(Long.valueOf(tccId), file);
        return "Documento adicionado com sucesso";
    }

    @GetMapping("/get/{tccId}/document")
    private ResponseEntity<ByteArrayResource> salvaDocumento(@PathVariable("tccId") String tccId){
        Doc doc = docStorageService.getDocument(Long.valueOf(tccId));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getDocType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+doc.getDocName()+"\"")
                .body(new ByteArrayResource(doc.getData()));
    }

    @PutMapping("/update/{tccId}/document")
    private String atualizaDocumento(@PathVariable("tccId") String tccId, @RequestBody MultipartFile file){
        docStorageService.atualiza(Long.valueOf(tccId), file);
        return "Documento atualizado com sucesso";
    }

    @DeleteMapping("/delete/{tccId}/document")
    private String deleteDocumento(@PathVariable("tccId") String tccId){
        docStorageService.delete(Long.valueOf(tccId));
        return "Documento deletado com sucesso";
    }


}
