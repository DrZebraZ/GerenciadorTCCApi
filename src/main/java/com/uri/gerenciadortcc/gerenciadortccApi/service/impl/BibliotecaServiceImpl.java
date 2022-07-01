package com.uri.gerenciadortcc.gerenciadortccApi.service.impl;

import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.BibliotecaObject;
import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.CursoBibliotecaObject;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.BibliotecaDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Biblioteca;
import com.uri.gerenciadortcc.gerenciadortccApi.model.repository.BibliotecaRepository;
import com.uri.gerenciadortcc.gerenciadortccApi.service.BibliotecaService;
import com.uri.gerenciadortcc.gerenciadortccApi.service.DocStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class BibliotecaServiceImpl implements BibliotecaService {

    @Autowired
    private BibliotecaRepository bibliotecaRepository;

    @Autowired
    private DocStorageService docStorageService;

    @Override
    public BibliotecaDTO addBiblioteca(BibliotecaObject bibliotecaObject) {
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setNomeAluno(bibliotecaObject.getNomeAluno());
        biblioteca.setTituloTCC(bibliotecaObject.getTituloTCC());
        biblioteca.setNomeOrientador(bibliotecaObject.getNomeOrientador());
        biblioteca.setDescricaoTCC(bibliotecaObject.getDescricaoTCC());
        biblioteca.setNomeCurso(bibliotecaObject.getNomeCurso());
        Biblioteca bibliotecaEntity = bibliotecaRepository.save(biblioteca);
        if(bibliotecaObject.getDocumento() != null){
            docStorageService.saveFileBiblioteca(bibliotecaEntity.getId(), bibliotecaObject.getDocumento());
        }
        return parseBiblioteDTO(bibliotecaEntity);
    }


    @Override
    public BibliotecaDTO getBiblioteca(Long bibliotecaId) {
        Optional<Biblioteca> biblioteca = bibliotecaRepository.findById(bibliotecaId);
        if(biblioteca.isPresent()){
            return parseBiblioteDTO(biblioteca.get());
        }else {
            throw new RuntimeException();
        }
    }

    @Override
    public List<BibliotecaDTO> getBibliotecasPorCurso(CursoBibliotecaObject cursoBibliotecaObject) {
        List<Biblioteca> bibliotecas = bibliotecaRepository.findByCursoNome(cursoBibliotecaObject.getNomeCurso());
        if(bibliotecas != null && !bibliotecas.isEmpty()){
            List<BibliotecaDTO> bibliotecaDTOS = new ArrayList<>();
            bibliotecas.sort(Comparator.comparing(Biblioteca::getTituloTCC));
            for(Biblioteca biblioteca:bibliotecas){
                bibliotecaDTOS.add(parseBiblioteDTO(biblioteca));
            }
            return bibliotecaDTOS;
        }else {
            throw new RuntimeException();
        }
    }

    @Override
    public List<BibliotecaDTO> getBibliotecasProTitulo(String tituloTCC) {
        List<Biblioteca> bibliotecas = bibliotecaRepository.findByTituloTCC(tituloTCC);
        if(bibliotecas != null && !bibliotecas.isEmpty()){
            List<BibliotecaDTO> bibliotecaDTOS = new ArrayList<>();
            bibliotecas.sort(Comparator.comparing(Biblioteca::getTituloTCC));
            for(Biblioteca biblioteca:bibliotecas){
                bibliotecaDTOS.add(parseBiblioteDTO(biblioteca));
            }
            return bibliotecaDTOS;
        }else {
            throw new RuntimeException();
        }
    }

    @Override
    public BibliotecaDTO atualizaDTO(Long bibliotecaId, BibliotecaObject bibliotecaObject) {
        Optional<Biblioteca> biblioteca = bibliotecaRepository.findById(bibliotecaId);
        if(biblioteca.isPresent()){
            biblioteca.get().setNomeAluno(bibliotecaObject.getNomeAluno());
            biblioteca.get().setTituloTCC(bibliotecaObject.getTituloTCC());
            biblioteca.get().setNomeOrientador(bibliotecaObject.getNomeOrientador());
            biblioteca.get().setDescricaoTCC(bibliotecaObject.getDescricaoTCC());
            biblioteca.get().setNomeCurso(bibliotecaObject.getNomeCurso());
            Biblioteca bibliotecaEntity = bibliotecaRepository.save(biblioteca.get());
            return parseBiblioteDTO(bibliotecaEntity);
        }else {
            throw new RuntimeException();
        }
    }

    @Override
    public void deleteBiblioteca(Long bibliotecaId) {
        Optional<Biblioteca> biblioteca = bibliotecaRepository.findById(bibliotecaId);
        if(biblioteca.isPresent()){
            bibliotecaRepository.deleteById(bibliotecaId);
        }else {
            throw new RuntimeException();
        }
    }

    private BibliotecaDTO parseBiblioteDTO(Biblioteca bibliotecaEntity) {
        BibliotecaDTO bibliotecaDTO = new BibliotecaDTO();
        bibliotecaDTO.setId(bibliotecaEntity.getId());
        bibliotecaDTO.setDescricaoTCC(bibliotecaEntity.getDescricaoTCC());
        bibliotecaDTO.setNomeAluno(bibliotecaEntity.getNomeAluno());
        bibliotecaDTO.setNomeOrientador(bibliotecaEntity.getNomeOrientador());
        bibliotecaDTO.setNomeCurso(bibliotecaEntity.getNomeCurso());
        bibliotecaDTO.setTituloTCC(bibliotecaEntity.getTituloTCC());
        if(bibliotecaEntity.getArquivo() != null){
            bibliotecaDTO.setIdDoc(bibliotecaEntity.getArquivo().getId());
        }
        return bibliotecaDTO;
    }
}
