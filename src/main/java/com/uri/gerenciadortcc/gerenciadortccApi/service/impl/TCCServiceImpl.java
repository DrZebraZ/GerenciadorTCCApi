package com.uri.gerenciadortcc.gerenciadortccApi.service.impl;

import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.TCCObject;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.AlunoDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.ProfessorDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.TCCDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Aluno;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Professor;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.TCC;
import com.uri.gerenciadortcc.gerenciadortccApi.model.repository.AlunoRepository;
import com.uri.gerenciadortcc.gerenciadortccApi.model.repository.ProfessorRepository;
import com.uri.gerenciadortcc.gerenciadortccApi.model.repository.TCCRepository;
import com.uri.gerenciadortcc.gerenciadortccApi.service.NotificacaoService;
import com.uri.gerenciadortcc.gerenciadortccApi.service.TCCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TCCServiceImpl implements TCCService {

    @Autowired
    private TCCRepository tccRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private NotificacaoService notificacaoService;

    @Autowired
    private AlunoRepository alunoRepository;

    @Override
    public TCCDTO salvaTCC(TCCObject tccObject) {
        TCC tcc = new TCC();
        tcc.setDescricao(tccObject.getDescricao());
        tcc.setTitulo(tccObject.getTitulo());
        Optional<Aluno> aluno = alunoRepository.findById(tccObject.getAlunoId());
        if(aluno.isPresent()){
            tcc.setAluno(aluno.get());
        }

        Optional<Professor> professor = professorRepository.findById(tccObject.getProfessorId());
        if(professor.isPresent()){
            tcc.setOrientador(professor.get());
        }

        tccRepository.save(tcc);

        return parseTCCDTO(tcc);

        ///implementar notificacao

    }

    @Override
    public TCCDTO getTCC(Long tccId) {
        Optional<TCC> tcc =  tccRepository.findById(tccId);
        if(tcc.isPresent()){
            return parseTCCDTO(tcc.get());
        }else{
            throw new RuntimeException();
        }
    }


    @Override
    public void atualizaTCC(Long tccId, TCCObject tccObject) {
        Optional<TCC> tcc = tccRepository.findById(tccId);
        if(tcc.isPresent()){
            TCC tccAtualizado = tcc.get();
            tccAtualizado.setDescricao(tccObject.getDescricao());
            tccAtualizado.setTitulo(tccObject.getTitulo());
            Optional<Professor> professor = professorRepository.findById(tccObject.getProfessorId());
            if(professor.isPresent()){
                tccAtualizado.setOrientador(professor.get());
            }
            Optional<Aluno> aluno = alunoRepository.findById(tccObject.getAlunoId());
            if(aluno.isPresent()){
                tccAtualizado.setAluno(aluno.get());
            }

            tccRepository.save(tccAtualizado);
        }
    }

    @Override
    public void deleteTCC(Long tccId) {
        tccRepository.deleteById(tccId);
    }

    private TCCDTO parseTCCDTO(TCC present) {
        TCCDTO tccdto = new TCCDTO();
        tccdto.setId(present.getIdTCC());
        if(present.getArquivo()!= null){
            tccdto.setDocId(present.getArquivo().getId());
        }
        tccdto.setTitulo(present.getTitulo());
        tccdto.setDescricao(present.getDescricao());
        AlunoDTO alunoDTO = new AlunoDTO();
        alunoDTO.setId(present.getAluno().getId());
        alunoDTO.setNome(present.getAluno().getNome());
        tccdto.setAlunoDTO(alunoDTO);
        ProfessorDTO professorDTO = new ProfessorDTO();
        professorDTO.setId(present.getOrientador().getId());
        professorDTO.setNome(present.getOrientador().getNome());
        tccdto.setProfessor(professorDTO);
        return tccdto;
    }
}
