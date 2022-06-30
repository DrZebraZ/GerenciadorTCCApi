package com.uri.gerenciadortcc.gerenciadortccApi.service.impl;

import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Aluno;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Doc;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Professor;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.TCC;
import com.uri.gerenciadortcc.gerenciadortccApi.model.repository.AlunoRepository;
import com.uri.gerenciadortcc.gerenciadortccApi.model.repository.DocRepository;
import com.uri.gerenciadortcc.gerenciadortccApi.model.repository.ProfessorRepository;
import com.uri.gerenciadortcc.gerenciadortccApi.model.repository.TCCRepository;
import com.uri.gerenciadortcc.gerenciadortccApi.service.DocStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DocStorageServiceImpl implements DocStorageService {

  @Autowired
  private DocRepository docRepository;

  @Autowired
  private TCCRepository tccRepository;

  @Autowired
  private AlunoRepository alunoRepository;

  @Autowired
  private ProfessorRepository professorRepository;

  @Override
  public Doc saveFile(Long tccId, MultipartFile file) {
	  String docname = file.getName();
	  try {
		  Doc doc = new Doc(docname,file.getContentType(),file.getBytes());
		  Optional<TCC> tcc = tccRepository.findById(tccId);
		  doc.setTcc(tcc.get());
		  Doc docEntity = docRepository.save(doc);
		  if(tcc.isPresent()){
		  	tcc.get().setArquivo(docEntity);
		  	tccRepository.save(tcc.get());
		  }
		  return docEntity;
	  }
	  catch(Exception e) {
		  e.printStackTrace();
	  }
	  return null;
  }

	@Override
	public Doc atualiza(Long tccId, MultipartFile file) {
		Optional<TCC> tcc = tccRepository.findById(tccId);
		try {
			if (tcc.isPresent()) {
				Doc docEntity = tcc.get().getArquivo();
				docEntity.setDocName(file.getName());
				docEntity.setDocType(file.getContentType());
				docEntity.setData(file.getBytes());
				docRepository.save(docEntity);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Doc getDocument(Long tccId) {
		Optional<TCC> tcc = tccRepository.findById(tccId);
		if(tcc.isPresent()){
			return tcc.get().getArquivo();
		}
		return null;
	}

	@Override
	public Doc delete(Long tccId) {
		Optional<TCC> tcc = tccRepository.findById(tccId);
		if(tcc.isPresent()){
			Doc docEntity = tcc.get().getArquivo();
			tcc.get().setArquivo(null);
			tccRepository.save(tcc.get());
			docRepository.deleteById(docEntity.getId());
		}
		return null;
	}

	@Override
	public Doc saveFileProfessor(Long professorId, MultipartFile file) {
		String docname = file.getName();
		try {
			Doc doc = new Doc(docname,file.getContentType(),file.getBytes());
			Doc docEntity = docRepository.save(doc);
			Optional<Professor> professor = professorRepository.findById(professorId);
			if(professor.isPresent()){
				professor.get().setArquivo(docEntity);
				professorRepository.save(professor.get());
			}
			return docEntity;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Doc atualizaDocProfessor(Long professorId, MultipartFile file) {
		Optional<Professor> professor = professorRepository.findById(professorId);
		try {
			if (professor.isPresent()) {
				Doc docEntity = professor.get().getArquivo();
				docEntity.setDocName(file.getName());
				docEntity.setDocType(file.getContentType());
				docEntity.setData(file.getBytes());
				docRepository.save(docEntity);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ByteArrayResource getDocumentProfessor(Long professorId) {
		Optional<Professor> professor = professorRepository.findById(professorId);
		if(professor.isPresent()){
			Doc docEntity = professor.get().getArquivo();
			return new ByteArrayResource(docEntity.getData());
		}
		return null;
	}

	@Override
	public Doc deleteDocProfessor(Long professorId) {
		Optional<Professor> professor = professorRepository.findById(professorId);
		if(professor.isPresent()){
			Doc docEntity = professor.get().getArquivo();
			professor.get().setArquivo(null);
			professorRepository.save(professor.get());
			docRepository.deleteById(docEntity.getId());
		}
		return null;
	}

	@Override
	public Doc saveFileAluno(Long alunoId, MultipartFile file) {
		String docname = file.getName();
		try {
			Doc doc = new Doc(docname,file.getContentType(),file.getBytes());
			Doc docEntity = docRepository.save(doc);
			Optional<Aluno> aluno = alunoRepository.findById(alunoId);
			if(aluno.isPresent()){
				aluno.get().setArquivo(docEntity);
				alunoRepository.save(aluno.get());
			}
			return docEntity;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Doc atualizaDocAluno(Long alunoId, MultipartFile file) {
		Optional<Aluno> aluno = alunoRepository.findById(alunoId);
		try {
			if (aluno.isPresent()) {
				Doc docEntity = aluno.get().getArquivo();
				docEntity.setDocName(file.getName());
				docEntity.setDocType(file.getContentType());
				docEntity.setData(file.getBytes());
				docRepository.save(docEntity);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ByteArrayResource getDocumentAluno(Long alunoId) {
		Optional<Aluno> aluno = alunoRepository.findById(alunoId);
		if(aluno.isPresent()){
			Doc docEntity = aluno.get().getArquivo();
			return new ByteArrayResource(docEntity.getData());
		}
		return null;
	}

	@Override
	public Doc deleteDocAluno(Long alunoId) {
		Optional<Aluno> aluno = alunoRepository.findById(alunoId);
		if(aluno.isPresent()){
			Doc docEntity = aluno.get().getArquivo();
			aluno.get().setArquivo(null);
			alunoRepository.save(aluno.get());
			docRepository.deleteById(docEntity.getId());
		}
		return null;
	}

	public Optional<Doc> getFile(Long fileId) {
	  return docRepository.findById(fileId);
  }
  public List<Doc> getFiles(){
	  return docRepository.findAll();
  }
}
