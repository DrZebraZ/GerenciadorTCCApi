package com.uri.gerenciadortcc.gerenciadortccApi.controller;

import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.BibliotecaObject;
import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.CursoBibliotecaObject;
import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.TituloTCCBibliotecaObject;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.BibliotecaDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Doc;
import com.uri.gerenciadortcc.gerenciadortccApi.service.BibliotecaService;
import com.uri.gerenciadortcc.gerenciadortccApi.service.DocStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/biblioteca")
public class BibliotecaController {

    @Autowired
    private BibliotecaService bibliotecaService;

    @Autowired
    private DocStorageService docStorageService;

    @PostMapping("/add")
    public BibliotecaDTO addBiblioteca(@RequestBody BibliotecaObject bibliotecaObject){
        return bibliotecaService.addBiblioteca(bibliotecaObject);
    }

    @GetMapping("/getBiblioteca/{bibliotecaId}")
    public BibliotecaDTO getBiblioteca(@PathVariable("bibliotecaId") String bibliotecaId){
        return bibliotecaService.getBiblioteca(Long.valueOf(bibliotecaId));
    }

    @GetMapping("/getBiblioteca/curso")
    public List<BibliotecaDTO> getBibliotecaPorCurso(@RequestBody CursoBibliotecaObject cursoBibliotecaObject){
        return bibliotecaService.getBibliotecasPorCurso(cursoBibliotecaObject);
    }

    @GetMapping("/getBiblioteca/tituloTCC")
    public List<BibliotecaDTO> getBibliotecaPorTituloTCC(@RequestBody TituloTCCBibliotecaObject tituloTCCBibliotecaObject){
        return bibliotecaService.getBibliotecasProTitulo(tituloTCCBibliotecaObject.getTituloTCC());
    }

    @PutMapping("/{bibliotecaId}/update")
    private BibliotecaDTO putBiblioteca(@PathVariable("bibliotecaId")String bibliotecaId, @RequestBody BibliotecaObject bibliotecaObject) {
        return bibliotecaService.atualizaDTO(Long.valueOf(bibliotecaId), bibliotecaObject);
    }

    @DeleteMapping("/{bibliotecaId}/update")
    private String deletaBiblioteca(@PathVariable("bibliotecaId")String bibliotecaId){
        bibliotecaService.deleteBiblioteca(Long.valueOf(bibliotecaId));
        return "Tcc da biblioteca deletado com Sucesso";
    }

    @PostMapping("/add/{bibliotecaId}/document")
    private ResponseEntity<ByteArrayResource> salvaDocumento(@PathVariable("bibliotecaId") String bibliotecaId, @RequestBody MultipartFile file){
        Doc doc = docStorageService.saveFileBiblioteca(Long.valueOf(bibliotecaId), file);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getDocType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+doc.getDocName()+"\"")
                .body(new ByteArrayResource(doc.getData()));
    }

    @GetMapping("/get/{bibliotecaId}/document")
    private ResponseEntity<InputStreamResource> getDocumento(@PathVariable("bibliotecaId") String bibliotecaId){
        Doc doc =  docStorageService.getDocumentBiblioteca(Long.valueOf(bibliotecaId));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+doc.getDocName()+"\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType(doc.getDocType()))
                .body(new InputStreamResource(new ByteArrayInputStream(doc.getData())));
    }

    @PutMapping("/update/{bibliotecaId}/document")
    private ResponseEntity<ByteArrayResource> atualizaDocumento(@PathVariable("bibliotecaId") String bibliotecaId, @RequestBody MultipartFile file){
        Doc doc = docStorageService.atualizaDocBiblioteca(Long.valueOf(bibliotecaId), file);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getDocType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+doc.getDocName()+"\"")
                .body(new ByteArrayResource(doc.getData()));
    }

    @DeleteMapping("/delete/{bibliotecaId}/document")
    private String deleteDocumento(@PathVariable("bibliotecaId") String bibliotecaId){
        docStorageService.deleteDocBiblioteca(Long.valueOf(bibliotecaId));
        return "Documento deletado com sucesso";
    }

}
