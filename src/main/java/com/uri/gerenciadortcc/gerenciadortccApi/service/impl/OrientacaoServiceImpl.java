package com.uri.gerenciadortcc.gerenciadortccApi.service.impl;

import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.ComentarioObject;
import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.DataOrientacaoObject;
import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.OrientacaoObject;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.*;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.*;
import com.uri.gerenciadortcc.gerenciadortccApi.model.enums.TipoUsuario;
import com.uri.gerenciadortcc.gerenciadortccApi.model.repository.*;
import com.uri.gerenciadortcc.gerenciadortccApi.service.DocStorageService;
import com.uri.gerenciadortcc.gerenciadortccApi.service.EmailService;
import com.uri.gerenciadortcc.gerenciadortccApi.service.OrientacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrientacaoServiceImpl implements OrientacaoService {

    @Autowired
    private OrientacaoRepository orientacaoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ComentariosRepository comentariosRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private DataOrientacaoRepository dataOrientacaoRepository;

    @Autowired
    private DocStorageService docStorageService;

    @Override
    public OrientacaoDTO addOrientacao(OrientacaoObject orientacaoObject) {
        Optional<Aluno> aluno = alunoRepository.findById(orientacaoObject.getAlunoId());
        Optional<Professor> professor = professorRepository.findById(orientacaoObject.getProfessorId());
        if(aluno.isPresent() && professor.isPresent()){
            Orientacao orientacao = new Orientacao();
            orientacao.setTituloTCC(aluno.get().getTcc().getTitulo());
            orientacao.setAluno(aluno.get());
            orientacao.setProfessor(professor.get());
            return parseOrientacaoDTO(orientacaoRepository.save(orientacao));
        }else {
            throw new RuntimeException();
        }
    }

    @Override
    public OrientacaoDTO addComentarioProfessor(Long orientacaoId, ComentarioObject comentarioObject) {
        Optional<Orientacao> orientacao = orientacaoRepository.findById(orientacaoId);
        if(orientacao.isPresent()){
            Comentarios comentario = new Comentarios();
            comentario.setComentario(comentarioObject.getComentario());
            comentario.setDataComentario(LocalDateTime.now());
            comentario.setOrientacao(orientacao.get());
            comentario.setAutor(TipoUsuario.PROFESSOR);
            comentariosRepository.save(comentario);
            Orientacao orientacaoEntity = orientacao.get();
            emailService.notificaComentario(orientacaoEntity, comentario);
            if(orientacaoEntity.getComentarios() != null){
                List<Comentarios> comentariosList = orientacaoEntity.getComentarios();
                comentariosList.add(comentario);
                orientacaoEntity.setComentarios(comentariosList);
                return parseOrientacaoDTO(orientacaoRepository.save(orientacaoEntity));
            }else {
                List<Comentarios> comentariosList = new ArrayList<>();
                comentariosList.add(comentario);
                orientacaoEntity.setComentarios(comentariosList);
                return parseOrientacaoDTO(orientacaoRepository.save(orientacaoEntity));
            }
        }else{
            throw new RuntimeException();
        }
    }

    @Override
    public OrientacaoDTO addComentarioAluno(Long orientacaoId, ComentarioObject comentarioObject) {
        Optional<Orientacao> orientacao = orientacaoRepository.findById(orientacaoId);
        if(orientacao.isPresent()){
            Comentarios comentario = new Comentarios();
            comentario.setComentario(comentarioObject.getComentario());
            comentario.setDataComentario(LocalDateTime.now());
            comentario.setOrientacao(orientacao.get());
            comentario.setAutor(TipoUsuario.ALUNO);
            comentariosRepository.save(comentario);
            Orientacao orientacaoEntity = orientacao.get();
            emailService.notificaComentario(orientacaoEntity, comentario);
            if(orientacaoEntity.getComentarios() != null){
                List<Comentarios> comentariosList = orientacaoEntity.getComentarios();
                comentariosList.add(comentario);
                orientacaoEntity.setComentarios(comentariosList);
                return parseOrientacaoDTO(orientacaoRepository.save(orientacaoEntity));
            }else {
                List<Comentarios> comentariosList = new ArrayList<>();
                comentariosList.add(comentario);
                orientacaoEntity.setComentarios(comentariosList);
                return parseOrientacaoDTO(orientacaoRepository.save(orientacaoEntity));
            }
        }else{
            throw new RuntimeException();
        }
    }

    @Override
    public ComentariosDTO getComentarios(Long orientacaoId) {
        Optional<Orientacao> orientacao = orientacaoRepository.findById(orientacaoId);
        if(orientacao.isPresent()){
            return parseComentarioDTO(orientacao.get().getComentarios());
        }else {
            throw new RuntimeException();
        }
    }

    @Override
    public OrientacaoDTO getOrientacao(Long orientacaoId) {
        Optional<Orientacao> orientacao = orientacaoRepository.findById(orientacaoId);
        if(orientacao.isPresent()){
            return parseOrientacaoDTO(orientacao.get());
        }else {
            throw new RuntimeException();
        }
    }

    @Override
    public OrientacaoDTO getOrientacaoPorAluno(Long alunoId) {
        Optional<Aluno> aluno = alunoRepository.findById(alunoId);
        if(aluno.isPresent()){
            Aluno alunoEntity = aluno.get();
            if(alunoEntity.getOrientacao() != null){
                return parseOrientacaoDTO(alunoEntity.getOrientacao());
            }else {
                throw new RuntimeException();
            }
        }else {
            throw new RuntimeException();
        }
    }

    @Override
    public List<OrientacaoDTO> getOrientacaoPorProfessor(Long professorId) {
        Optional<Professor> professor = professorRepository.findById(professorId);
        if(professor.isPresent()){
            Professor professorEntity = professor.get();
            if(professorEntity.getOrientacoes() != null && !professorEntity.getOrientacoes().isEmpty()){
               List<Orientacao> orientacoes =  professorEntity.getOrientacao();
               List<OrientacaoDTO> orientacaoDTOList = new ArrayList<>();
               for(Orientacao orientacao: orientacoes){
                   orientacaoDTOList.add(parseOrientacaoDTO(orientacao));
               }
               return orientacaoDTOList;
            }else {
                throw new RuntimeException();
            }
        }else {
            throw new RuntimeException();
        }
    }

    @Override
    public OrientacaoDTO atualizaComentario(Long comentarioId, ComentarioObject comentarioObject) {
        Optional<Comentarios> comentarios = comentariosRepository.findById(comentarioId);
        if(comentarios.isPresent()){
            Comentarios comentario = comentarios.get();
            comentario.setComentario(comentario.getComentario());
            comentario.setDataComentario(LocalDateTime.now());
            comentariosRepository.save(comentario);
            return parseOrientacaoDTO(comentario.getOrientacao());
        }else {
            throw new RuntimeException();
        }
    }

    @Override
    public OrientacaoDTO deletaComentario(Long comentarioId) {
        Optional<Comentarios> comentarios = comentariosRepository.findById(comentarioId);
        if(comentarios.isPresent()){
            Orientacao orientacao = comentarios.get().getOrientacao();
            comentariosRepository.deleteById(comentarioId);
            return parseOrientacaoDTO(orientacao);
        }else{
            throw  new RuntimeException();
        }
    }

    @Override
    public OrientacaoDTO marcaOrientacao(Long valueOf, DataOrientacaoObject dataOrientacao) {
        Optional<Orientacao> orientacao = orientacaoRepository.findById(valueOf);
        if(orientacao.isPresent()){
            Orientacao orientacaoEntity = orientacao.get();
            if(orientacaoEntity.getDatasOrientacoes() != null){
                List<DataOrientacao> dataOrientacaos = orientacaoEntity.getDatasOrientacoes();
                DataOrientacao novaDataOrientacao =  new DataOrientacao();
                novaDataOrientacao.setDataOrientacao(dataOrientacao.getData());
                novaDataOrientacao.setOrientacao(orientacaoEntity);
                dataOrientacaoRepository.save(novaDataOrientacao);
                dataOrientacaos.add(novaDataOrientacao);
                emailService.notificaDataOrientacao(orientacaoEntity, dataOrientacao.getData());
                return parseOrientacaoDTO(orientacaoRepository.save(orientacaoEntity));
            }else {
                List<DataOrientacao> dataOrientacaos = new ArrayList<>();
                DataOrientacao novaDataOrientacao =  new DataOrientacao();
                novaDataOrientacao.setDataOrientacao(dataOrientacao.getData());
                novaDataOrientacao.setOrientacao(orientacaoEntity);
                dataOrientacaoRepository.save(novaDataOrientacao);
                dataOrientacaos.add(novaDataOrientacao);
                emailService.notificaDataOrientacao(orientacaoEntity, dataOrientacao.getData());
                return parseOrientacaoDTO(orientacaoRepository.save(orientacaoEntity));
            }
        }else{
            throw new RuntimeException();
        }
    }

    @Override
    public OrientacaoDTO atualizaDataOrientacao(Long dataOrientacaoId, DataOrientacaoObject dataOrientacao) {
        Optional<DataOrientacao> dataOrientacaoOptional = dataOrientacaoRepository.findById(dataOrientacaoId);
        if(dataOrientacaoOptional.isPresent()){
            DataOrientacao dataOrientacaoEntity = dataOrientacaoOptional.get();
            dataOrientacaoEntity.setDataOrientacao(dataOrientacao.getData());
            dataOrientacaoRepository.save(dataOrientacaoEntity);
            emailService.notificaDataOrientacao(dataOrientacaoEntity.getOrientacao(), dataOrientacao.getData());
            return parseOrientacaoDTO(dataOrientacaoEntity.getOrientacao());
        }else {
            throw new RuntimeException();
        }
    }

    @Override
    public OrientacaoDTO deletaDataOrientacao(Long dataOrientacaoId) {
        Optional<DataOrientacao> dataOrientacao = dataOrientacaoRepository.findById(dataOrientacaoId);
        if(dataOrientacao.isPresent()){
            Orientacao orientacao = dataOrientacao.get().getOrientacao();
            emailService.notificaDesmarcarDataOrientacao(dataOrientacao.get().getOrientacao(), dataOrientacao.get().getDataOrientacao());
            dataOrientacaoRepository.deleteById(dataOrientacaoId);
            return parseOrientacaoDTO(orientacao);
        }else{
            throw new RuntimeException();
        }
    }

    @Override
    public ByteArrayInputStream getRelatorioOrientacao(Long orientacaoId) throws IOException {
        Optional<Orientacao> orientacao = orientacaoRepository.findById(orientacaoId);
        if(orientacao.isPresent()){
            return docStorageService.getRelatorioOrientacao(orientacao.get());
        }else {
            throw new RuntimeException();
        }
    }

    @Override
    public void atualizaOrientacao(Orientacao orientacao, TCC tcc) {
        orientacao.setTituloTCC(tcc.getTitulo());
        orientacao.setProfessor(tcc.getOrientador());
        orientacao.setAluno(tcc.getAluno());
        orientacao.setTcc(tcc);
        orientacaoRepository.save(orientacao);
    }

    private ComentariosDTO parseComentarioDTO(List<Comentarios> comentarios) {
        ComentariosDTO comentariosDTO = new ComentariosDTO();
        List<ComentarioDTO> comentariosList = new ArrayList<>();
        for(Comentarios comentarioEntity : comentarios){
            ComentarioDTO comentario = new ComentarioDTO();
            comentario.setComentario(comentarioEntity.getComentario());
            comentario.setDataComentario(comentarioEntity.getDataComentario());
            comentario.setIdComentario(comentarioEntity.getIdComentario());
            comentario.setAutor(comentarioEntity.getAutor());
            comentariosList.add(comentario);
        }
        comentariosDTO.setComentarios(comentariosList);
        return comentariosDTO;
    }

    private OrientacaoDTO parseOrientacaoDTO(Orientacao orientacaoEntity){
        OrientacaoDTO orientacaoDTO = new OrientacaoDTO();
        orientacaoDTO.setOrientacacaoId(orientacaoEntity.getIdOrientacao());
        orientacaoDTO.setTituloTCC(orientacaoEntity.getTituloTCC());
        if(orientacaoEntity.getComentarios() != null){
            orientacaoDTO.setComentarios(parseComentarioDTO(orientacaoEntity.getComentarios()));
        }
        orientacaoDTO.setAlunoId(orientacaoEntity.getAluno().getId());
        orientacaoDTO.setNomeAluno(orientacaoEntity.getAluno().getNome());
        orientacaoDTO.setProfessorId(orientacaoEntity.getProfessor().getId());
        orientacaoDTO.setNomeProfessor(orientacaoEntity.getProfessor().getNome());
        if(orientacaoEntity.getDatasOrientacoes() != null){
            List<DataOrientacaoDTO> dataOrientacaoDTOS = new ArrayList<>();
            List<DataOrientacao> dataOrientacoes = orientacaoEntity.getDatasOrientacoes();
            for(DataOrientacao dataOrientacao : dataOrientacoes) {
                DataOrientacaoDTO dataOrientacaoDTO = new DataOrientacaoDTO();
                dataOrientacaoDTO.setIdDataOrientacao(dataOrientacao.getId());
                dataOrientacaoDTO.setData(dataOrientacao.getDataOrientacao());
                dataOrientacaoDTOS.add(dataOrientacaoDTO);
            }
            orientacaoDTO.setDatasOrientacoes(dataOrientacaoDTOS);
        }
        return orientacaoDTO;
    }
}
