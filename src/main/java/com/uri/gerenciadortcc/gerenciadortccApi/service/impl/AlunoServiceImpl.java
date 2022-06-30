package com.uri.gerenciadortcc.gerenciadortccApi.service.impl;

import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.UsuarioObject;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.AlunoLoginDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.TCCAlunoDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.exception.ErroAutenticacao;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Aluno;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Curso;
import com.uri.gerenciadortcc.gerenciadortccApi.model.enums.TipoUsuario;
import com.uri.gerenciadortcc.gerenciadortccApi.model.repository.AlunoRepository;
import com.uri.gerenciadortcc.gerenciadortccApi.model.repository.CursoRepository;
import com.uri.gerenciadortcc.gerenciadortccApi.service.AlunoService;
import com.uri.gerenciadortcc.gerenciadortccApi.service.DocStorageService;
import com.uri.gerenciadortcc.gerenciadortccApi.service.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AlunoServiceImpl implements AlunoService {

    @Autowired
    private AlunoRepository repository;

    @Autowired
    private NotificacaoService notificacaoService;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private DocStorageService docStorageService;

    @Override
    public Aluno salvarAluno(UsuarioObject usuarioObject) {
        Boolean validou = validarCredenciaisADDUsuario(usuarioObject);
        if (validou) {
            Aluno aluno = new Aluno();
            aluno.setNome(usuarioObject.getNome());
            aluno.setEmail(usuarioObject.getEmail());
            aluno.setSenha(usuarioObject.getSenha());
            aluno.setCpf(usuarioObject.getCpf());
            aluno.setDatanasc(usuarioObject.getDatanasc());
            aluno.setTipoUsuario(TipoUsuario.ALUNO);
            Optional<Curso> curso = cursoRepository.findById(usuarioObject.getCursoId());
            if(curso.isPresent()){
                aluno.setCurso(curso.get());
            }
            Aluno alunoEntity = repository.save(aluno);
            if(usuarioObject.getFoto() != null){
                docStorageService.saveFileAluno(alunoEntity.getId(), usuarioObject.getFoto());
            }
            notificacaoService.salvarNotificacaoNovoUsuarioAluno(aluno.getId());
            return aluno;
        }else return null;
    }

    @Override
    public AlunoLoginDTO Login(String email, String senha) {

        Optional<Aluno> aluno = repository.findByEmailAndSenha(email, senha);
        if(aluno.isPresent()){
           return parserAlunoLoginDTO(aluno.get());
        }
        throw new RuntimeException();
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
    public Long pegaIdAluno(Aluno aluno) {
        Optional<Aluno> user = repository.findByEmail(aluno.getEmail());
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

    private AlunoLoginDTO parserAlunoLoginDTO(Aluno aluno) {
        AlunoLoginDTO alunoLoginDTO = new AlunoLoginDTO();
        alunoLoginDTO.setId(aluno.getId());
        alunoLoginDTO.setCpf(aluno.getCpf());
        alunoLoginDTO.setDatanasc(aluno.getDatanasc());
        alunoLoginDTO.setNome(aluno.getNome());
        alunoLoginDTO.setEmail(aluno.getEmail());
        alunoLoginDTO.setIdCurso(aluno.getCurso().getIdCurso());
        alunoLoginDTO.setNomeCurso(aluno.getCurso().getNome());
        if(aluno.getTcc() != null){
            TCCAlunoDTO tccAlunoDTO = new TCCAlunoDTO();
            tccAlunoDTO.setIdTCC(aluno.getTcc().getIdTCC());
            tccAlunoDTO.setDescricao(aluno.getTcc().getDescricao());
            if(aluno.getTcc().getOrientador() != null){
                tccAlunoDTO.setIdProfessor(aluno.getTcc().getOrientador().getId());
                tccAlunoDTO.setNomeProfessor(aluno.getTcc().getOrientador().getNome());
            }
            alunoLoginDTO.setTccAlunoDTO(tccAlunoDTO);
        }
        return alunoLoginDTO;
    }
}
