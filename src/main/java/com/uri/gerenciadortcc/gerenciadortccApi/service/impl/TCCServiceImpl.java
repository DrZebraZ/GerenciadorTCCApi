package com.uri.gerenciadortcc.gerenciadortccApi.service.impl;

import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.BibliotecaObject;
import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.TCCObject;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.AlunoDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.ProfessorDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.TCCDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Aluno;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Doc;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Professor;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.TCC;
import com.uri.gerenciadortcc.gerenciadortccApi.model.repository.AlunoRepository;
import com.uri.gerenciadortcc.gerenciadortccApi.model.repository.DocRepository;
import com.uri.gerenciadortcc.gerenciadortccApi.model.repository.ProfessorRepository;
import com.uri.gerenciadortcc.gerenciadortccApi.model.repository.TCCRepository;
import com.uri.gerenciadortcc.gerenciadortccApi.service.BibliotecaService;
import com.uri.gerenciadortcc.gerenciadortccApi.service.EmailService;
import com.uri.gerenciadortcc.gerenciadortccApi.service.NotificacaoService;
import com.uri.gerenciadortcc.gerenciadortccApi.service.TCCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

    @Autowired
    private EmailService emailService;

    @Autowired
    private BibliotecaService bibliotecaService;

    @Autowired
    private DocRepository docRepository;

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

        TCC tccEntity = tccRepository.save(tcc);

        emailService.notificaEscolhaDeOrientador(tccEntity);

        return parseTCCDTO(tccEntity);

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
    public TCCDTO atualizaTCC(Long tccId, TCCObject tccObject) {
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

            return parseTCCDTO(tccRepository.save(tccAtualizado));
        }
        throw new RuntimeException();
    }

    @Override
    public void deleteTCC(Long tccId) {
        tccRepository.deleteById(tccId);
    }

    @Override
    public void colocaNaBiblioteca(Long tccId) {
        Optional<TCC> tcc = tccRepository.findById(tccId);
        if(tcc.isPresent() && tcc.get().getArquivo()!= null){
            bibliotecaService.addBiblioteca(parseTCCParaBiblioteca(tcc.get()));
            tccRepository.deleteById(tccId);
            docRepository.deleteById(tcc.get().getArquivo().getId());
        }else {
            throw new RuntimeException();
        }
    }

    private BibliotecaObject parseTCCParaBiblioteca(TCC tcc) {
        BibliotecaObject bibliotecaObject = new BibliotecaObject();
        bibliotecaObject.setTituloTCC(tcc.getTitulo());
        bibliotecaObject.setDescricaoTCC(tcc.getDescricao());
        bibliotecaObject.setNomeCurso(tcc.getAluno().getCurso().getNome());
        bibliotecaObject.setNomeOrientador(tcc.getOrientador().getNome());
        bibliotecaObject.setNomeAluno(tcc.getAluno().getNome());
        bibliotecaObject.setDocumento(parseArquivo(tcc.getArquivo()));
        return bibliotecaObject;
    }

    private MultipartFile parseArquivo(Doc arquivo) {
        return new MultipartFile() {
            @Override
            public String getName() {
                return arquivo.getDocName();
            }

            @Override
            public String getOriginalFilename() {
                return arquivo.getDocName();
            }

            @Override
            public String getContentType() {
                return arquivo.getDocType();
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 1000;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return arquivo.getData();
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {

            }
        };
    }

    private TCCDTO parseTCCDTO(TCC present) {
        TCCDTO tccdto = new TCCDTO();
        tccdto.setId(present.getId());
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
