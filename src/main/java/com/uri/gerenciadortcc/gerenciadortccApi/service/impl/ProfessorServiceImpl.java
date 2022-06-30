package com.uri.gerenciadortcc.gerenciadortccApi.service.impl;

import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.UsuarioObject;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.CursoReturnDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.ProfessorCompletoDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.ProfessorDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.TCCProfessorDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.exception.ErroAutenticacao;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Curso;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Professor;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.TCC;
import com.uri.gerenciadortcc.gerenciadortccApi.model.enums.TipoUsuario;
import com.uri.gerenciadortcc.gerenciadortccApi.model.repository.CursoRepository;
import com.uri.gerenciadortcc.gerenciadortccApi.model.repository.ProfessorRepository;
import com.uri.gerenciadortcc.gerenciadortccApi.service.NotificacaoService;
import com.uri.gerenciadortcc.gerenciadortccApi.service.ProfessorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProfessorServiceImpl implements ProfessorService {

    @Autowired
    private ProfessorRepository repository;

    @Autowired
    private NotificacaoService notificacaoService;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private DocStorageServiceImpl docStorageService;

    @Override
    public Professor salvarProfessor(UsuarioObject usuarioObject) {
        Boolean validou = validarCredenciaisADDUsuario(usuarioObject);
        if (validou) {
            Professor professor = new Professor();
            professor.setNome(usuarioObject.getNome());
            professor.setEmail(usuarioObject.getEmail());
            professor.setSenha(usuarioObject.getSenha());
            professor.setCpf(usuarioObject.getCpf());
            professor.setDatanasc(usuarioObject.getDatanasc());
            professor.setTipoUsuario(TipoUsuario.PROFESSOR);
            Optional<Curso> curso = cursoRepository.findById(usuarioObject.getCursoId());
            if(curso.isPresent()){
                List<Curso> cursos = new ArrayList<>();
                cursos.add(curso.get());
                professor.setCursos(cursos);
            }
            Professor professorEntity = repository.save(professor);
            if(usuarioObject.getFoto() != null){
                docStorageService.saveFileProfessor(professorEntity.getId(), usuarioObject.getFoto());
            }
            notificacaoService.salvarNotificacaoNovoUsuarioProfessor(professor.getId());
            return professor;
        }else return null;
    }

    @Override
    public ProfessorCompletoDTO Login(String email, String senha) {
        Optional<Professor> professor = repository.findByEmailAndSenha(email, senha);
        if(professor.isPresent()){
            return parseProfessorCompletoDTO(professor.get());
        }else {
            throw new RuntimeException();
        }
    }

    @Override
    public ArrayList<ProfessorDTO> getProfessorPorCurso(Long cursoId) {
    	ArrayList<Professor> lista = repository.getProfessorPorIDCurso(cursoId);
    	log.info("AQUIIIII {}", lista.size());
    	ArrayList<ProfessorDTO> listaRetorno = new ArrayList<ProfessorDTO>();
    	for (Professor professor: lista) {
    		ProfessorDTO prof = new ProfessorDTO();
    		prof.setId(professor.getId());
    		prof.setNome(professor.getNome());
    		listaRetorno.add(prof);
    	}
        return listaRetorno;
    }

    @Override
    public void transformaProfessorCoordenador(Long professorId) {
        Optional<Professor> professor = repository.findById(professorId);
        if(professor.isPresent()){
            Professor professorEntity = professor.get();
            professorEntity.setCoordenador(true);
            repository.save(professorEntity);
        }
    }

    @Override
    public ProfessorDTO getProfessorCoordenador(Long cursoId) {
        Optional<Professor> professor =  repository.getCoordenador(cursoId);
        if(professor.isPresent()){
            ProfessorDTO professorDTO = new ProfessorDTO();
            professorDTO.setId(professor.get().getId());
            professorDTO.setNome(professor.get().getNome());
            return professorDTO;
        }else {
            throw new RuntimeException();
        }

    }

    @Override
    public void adicionaCurso(Long professorId, Long cursoId) {
        Optional<Professor> professor = repository.findById(professorId);
        Optional<Curso> curso = cursoRepository.findById(cursoId);
        if(professor.isPresent() && curso.isPresent()){
            List<Curso> cursos = professor.get().getCursos();
            cursos.add(curso.get());
            professor.get().setCursos(cursos);
            repository.save(professor.get());
        }else {
            throw new RuntimeException();
        }

    }

    @Override
    public ProfessorCompletoDTO getProfessor(Long professorId) {
        Optional<Professor> professor = repository.findById(professorId);
        if(professor.isPresent()){
            return parseProfessorCompletoDTO(professor.get());
        }else {
            throw new RuntimeException();
        }
    }

    @Override
    public boolean validarEmail(String email) {
        boolean existe = repository.existsByEmail(email);
        if (existe){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Long pegaIdProfessor(Professor professor) {
        Optional<Professor> user = repository.findByEmail(professor.getEmail());
        Long id = user.get().getId();
        return id;
    }

    @Override
    public boolean validarCredenciaisADDUsuario(UsuarioObject usuarioObject){
        boolean existeEmail = validarEmail(usuarioObject.getEmail());
        if (existeEmail){
            throw new ErroAutenticacao("Email em uso");
        }
        return true;
    }

    private ProfessorCompletoDTO parseProfessorCompletoDTO(Professor professor) {
        ProfessorCompletoDTO professorCompletoDTO = new ProfessorCompletoDTO();
        professorCompletoDTO.setId(professor.getId());
        professorCompletoDTO.setNome(professor.getNome());
        professorCompletoDTO.setCpf(professor.getCpf());
        professorCompletoDTO.setDatanasc(professor.getDatanasc());
        professorCompletoDTO.setEmail(professor.getEmail());
        professorCompletoDTO.setCoordenador(professor.getCoordenador());
        if(professor.getOrientacoes() != null && !professor.getOrientacoes().isEmpty()){
            List<TCCProfessorDTO> tccProfessorDTOS = new ArrayList<>();
            for(TCC tcc : professor.getOrientacoes()){
                TCCProfessorDTO tccProfessorDTO = new TCCProfessorDTO();
                tccProfessorDTO.setIdTCC(tcc.getIdTCC());
                tccProfessorDTO.setDescricao(tcc.getDescricao());
                tccProfessorDTO.setIdAluno(tcc.getAluno().getId());
                tccProfessorDTO.setNomeAluno(tcc.getAluno().getNome());
                tccProfessorDTOS.add(tccProfessorDTO);
            }
            professorCompletoDTO.setTccs(tccProfessorDTOS);
        }
        if(professor.getCursos() != null && !professor.getCursos().isEmpty()){
            List<CursoReturnDTO> cursoReturnDTOS = new ArrayList<>();
            for(Curso curso : professor.getCursos()){
                CursoReturnDTO cursoReturnDTO = new CursoReturnDTO();
                cursoReturnDTO.setIdCurso(curso.getIdCurso());
                cursoReturnDTO.setNome(curso.getNome());
                cursoReturnDTO.setAreacurso(curso.getAreacurso());
                cursoReturnDTOS.add(cursoReturnDTO);
            }
            professorCompletoDTO.setCursos(cursoReturnDTOS);
        }
        return professorCompletoDTO;
    }
}
