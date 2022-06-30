package com.uri.gerenciadortcc.gerenciadortccApi.service.impl;

import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.ComentarioObject;
import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.OrientacaoObject;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.ComentarioDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.ComentariosDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.DataOrientacaoDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.OrientacaoDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.*;
import com.uri.gerenciadortcc.gerenciadortccApi.model.repository.*;
import com.uri.gerenciadortcc.gerenciadortccApi.service.EmailService;
import com.uri.gerenciadortcc.gerenciadortccApi.service.OrientacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public void addOrientacao(OrientacaoObject orientacaoObject) {
        Optional<Aluno> aluno = alunoRepository.findById(orientacaoObject.getAlunoId());
        Optional<Professor> professor = professorRepository.findById(orientacaoObject.getProfessorId());
        if(aluno.isPresent() && professor.isPresent()){
            Orientacao orientacao = new Orientacao();
            orientacao.setTituloTCC(aluno.get().getTcc().getDescricao());
            orientacao.setAluno(aluno.get());
            orientacao.setProfessor(professor.get());
            orientacaoRepository.save(orientacao);
        }else {
            throw new RuntimeException();
        }
    }

    @Override
    public void addComentario(Long orientacaoId, ComentarioObject comentarioObject) {
        Optional<Orientacao> orientacao = orientacaoRepository.findById(orientacaoId);
        if(orientacao.isPresent()){
            Comentarios comentario = new Comentarios();
            comentario.setComentario(comentario.getComentario());
            comentario.setDataComentario(LocalDate.now());
            comentario.setDescricao(comentario.getDescricao());
            comentariosRepository.save(comentario);
            Orientacao orientacaoEntity = orientacao.get();
            if(orientacaoEntity.getComentarios() != null){
                List<Comentarios> comentariosList = orientacaoEntity.getComentarios();
                comentariosList.add(comentario);
                orientacaoRepository.save(orientacaoEntity);
            }else {
                List<Comentarios> comentariosList = new ArrayList<>();
                comentariosList.add(comentario);
                orientacaoRepository.save(orientacaoEntity);
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
    public void atualizaComentario(Long comentarioId, ComentarioObject comentarioObject) {
        Optional<Comentarios> comentarios = comentariosRepository.findById(comentarioId);
        if(comentarios.isPresent()){
            Comentarios comentario = comentarios.get();
            comentario.setComentario(comentario.getComentario());
            comentario.setDataComentario(LocalDate.now());
            comentario.setDescricao(comentario.getDescricao());
            comentariosRepository.save(comentario);
        }else {
            throw new RuntimeException();
        }
    }

    @Override
    public void deletaComentario(Long comentarioId) {
        comentariosRepository.deleteById(comentarioId);
    }

    @Override
    public void marcaOrientacao(Long valueOf, LocalDateTime dataOrientacao) {
        Optional<Orientacao> orientacao = orientacaoRepository.findById(valueOf);
        if(orientacao.isPresent()){
            Orientacao orientacaoEntity = orientacao.get();
            if(orientacaoEntity.getDatasOrientacoes() != null){
                List<DataOrientacao> dataOrientacaos = orientacaoEntity.getDatasOrientacoes();
                DataOrientacao novaDataOrientacao =  new DataOrientacao();
                novaDataOrientacao.setDataOrientacao(dataOrientacao);
                dataOrientacaoRepository.save(novaDataOrientacao);
                dataOrientacaos.add(novaDataOrientacao);
                orientacaoRepository.save(orientacaoEntity);
            }else {
                List<DataOrientacao> dataOrientacaos = new ArrayList<>();
                DataOrientacao novaDataOrientacao =  new DataOrientacao();
                novaDataOrientacao.setDataOrientacao(dataOrientacao);
                dataOrientacaoRepository.save(novaDataOrientacao);
                dataOrientacaos.add(novaDataOrientacao);
                orientacaoRepository.save(orientacaoEntity);
            }
            emailService.notificaDataOrientacao(orientacaoEntity, dataOrientacao);
        }else{
            throw new RuntimeException();
        }
    }

    @Override
    public void atualizaDataOrientacao(Long dataOrientacaoId, LocalDateTime dataOrientacao) {
        Optional<DataOrientacao> dataOrientacaoOptional = dataOrientacaoRepository.findById(dataOrientacaoId);
        if(dataOrientacaoOptional.isPresent()){
            DataOrientacao dataOrientacaoEntity = dataOrientacaoOptional.get();
            dataOrientacaoEntity.setDataOrientacao(dataOrientacao);
            dataOrientacaoRepository.save(dataOrientacaoEntity);
            emailService.notificaDataOrientacao(dataOrientacaoEntity.getOrientacao(), dataOrientacao);
        }else {
            throw new RuntimeException();
        }
    }

    @Override
    public void deletaDataOrientacao(Long dataOrientacaoId) {
        Optional<DataOrientacao> dataOrientacao = dataOrientacaoRepository.findById(dataOrientacaoId);
        if(dataOrientacao.isPresent()){
            emailService.notificaDesmarcarDataOrientacao(dataOrientacao.get().getOrientacao(), dataOrientacao.get().getDataOrientacao());
            dataOrientacaoRepository.deleteById(dataOrientacaoId);
        }else{
            throw new RuntimeException();
        }
    }

    private ComentariosDTO parseComentarioDTO(List<Comentarios> comentarios) {
        ComentariosDTO comentariosDTO = new ComentariosDTO();
        List<ComentarioDTO> comentariosList = new ArrayList<>();
        for(Comentarios comentarioEntity : comentarios){
            ComentarioDTO comentario = new ComentarioDTO();
            comentario.setComentario(comentarioEntity.getComentario());
            comentario.setDescricao(comentarioEntity.getDescricao());
            comentario.setDataComentario(comentarioEntity.getDataComentario());
            comentario.setIdComentario(comentarioEntity.getIdComentario());
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
