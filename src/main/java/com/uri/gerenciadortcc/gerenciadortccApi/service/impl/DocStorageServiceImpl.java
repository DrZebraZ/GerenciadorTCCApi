package com.uri.gerenciadortcc.gerenciadortccApi.service.impl;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.*;
import com.uri.gerenciadortcc.gerenciadortccApi.model.repository.*;
import com.uri.gerenciadortcc.gerenciadortccApi.service.DocStorageService;
import com.uri.gerenciadortcc.gerenciadortccApi.utils.ReportUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
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

  @Autowired
  private BibliotecaRepository bibliotecaRepository;

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
				if(tcc.get().getArquivo() != null){
					Doc docEntity = tcc.get().getArquivo();
					docEntity.setDocName(file.getName());
					docEntity.setDocType(file.getContentType());
					docEntity.setData(file.getBytes());
					docRepository.save(docEntity);
					return docEntity;
				}
				Doc docEntity = new Doc();
				docEntity.setDocName(file.getName());
				docEntity.setDocType(file.getContentType());
				docEntity.setData(file.getBytes());
				Doc doc = docRepository.save(docEntity);
				tcc.get().setArquivo(doc);
				tccRepository.save(tcc.get());
				return doc;
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
	public Doc getDocumentProfessor(Long professorId) {
		Optional<Professor> professor = professorRepository.findById(professorId);
		if(professor.isPresent()){
			Doc docEntity = professor.get().getArquivo();
			return docEntity;
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
	public Doc getDocumentAluno(Long alunoId) {
		Optional<Aluno> aluno = alunoRepository.findById(alunoId);
		if(aluno.isPresent()){
			Doc docEntity = aluno.get().getArquivo();
			return docEntity;
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

	@Override
	public Doc saveFileBiblioteca(Long bibliotecaId, MultipartFile file) {
		String docname = file.getName();
		try {
			Doc doc = new Doc(docname,file.getContentType(),file.getBytes());
			Doc docEntity = docRepository.save(doc);
			Optional<Biblioteca> biblioteca = bibliotecaRepository.findById(bibliotecaId);
			if(biblioteca.isPresent()){
				biblioteca.get().setArquivo(docEntity);
				bibliotecaRepository.save(biblioteca.get());
			}
			return docEntity;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Doc atualizaDocBiblioteca(Long bibliotecaId, MultipartFile file) {
		Optional<Biblioteca> biblioteca = bibliotecaRepository.findById(bibliotecaId);
		try {
			if (biblioteca.isPresent()) {
				Doc docEntity = biblioteca.get().getArquivo();
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
	public Doc getDocumentBiblioteca(Long bibliotecaId) {
		Optional<Biblioteca> biblioteca = bibliotecaRepository.findById(bibliotecaId);
		if(biblioteca.isPresent()){
			Doc docEntity = biblioteca.get().getArquivo();
			return docEntity;
		}
		return null;
	}

	@Override
	public Doc deleteDocBiblioteca(Long bibliotecaId) {
		Optional<Biblioteca> biblioteca = bibliotecaRepository.findById(bibliotecaId);
		if(biblioteca.isPresent()){
			Doc docEntity = biblioteca.get().getArquivo();
			biblioteca.get().setArquivo(null);
			bibliotecaRepository.save(biblioteca.get());
			docRepository.deleteById(docEntity.getId());
		}
		return null;
	}

	@Override
	public ByteArrayInputStream getRelatorioOrientacao(Orientacao orientacao) throws IOException {
		ReportUtils report = ReportUtils.getInstance();
		report.setPageSize(PageSize.A4.rotate());

		report.addParagraph(new Paragraph("Relatório de Orientações gerado no dia " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
				.setFontSize(28)
				.setTextAlignment(TextAlignment.CENTER)
				.setFont(PdfFontFactory.createFont(StandardFonts.COURIER_BOLD))
		);

		report.addNewLine();

		report.addParagraph(new Paragraph("Orientações do aluno " + orientacao.getAluno().getNome() + " do curso de " + orientacao.getAluno().getCurso().getNome())
				.setFontSize(17)
				.setTextAlignment(TextAlignment.CENTER)
				.setFont(PdfFontFactory.createFont(StandardFonts.COURIER_BOLD)));

		List<DataOrientacao> dataOrientacoes = orientacao.getDatasOrientacoes();
		if(dataOrientacoes != null && !dataOrientacoes.isEmpty()){
			report.addNewLine();
			report.addParagraph(new Paragraph("Lista de Datas de Orientações:")
					.setFontSize(15)
					.setTextAlignment(TextAlignment.CENTER)
					.setFont(PdfFontFactory.createFont(StandardFonts.COURIER_BOLD))
			);
			report.addNewLine();
			report.openTable(1);
			report.addTableHeader("DATAS DE ORIENTAÇÕES");
			dataOrientacoes.stream().sorted(Comparator
					.comparing((DataOrientacao d) -> d.getDataOrientacao())
					.thenComparing(DataOrientacao::getDataOrientacao))
					.forEach(data -> {
						report.addTableColumn(data.getDataOrientacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
					});
			report.closeTable();
		}
		List<Comentarios> comentarios = orientacao.getComentarios();
		if(comentarios != null && !comentarios.isEmpty()){
			report.addNewLine();
			report.addParagraph(new Paragraph("Lista de Comentários realizados na página:")
					.setFontSize(15)
					.setTextAlignment(TextAlignment.CENTER)
					.setFont(PdfFontFactory.createFont(StandardFonts.COURIER_BOLD))
			);
			report.addNewLine();
			report.openTable(3);
			report.addTableHeader("DATA", "AUTOR", "COMENTÁRIO");
			comentarios.stream().sorted(Comparator
					.comparing((Comentarios c) -> c.getDataComentario())
					.thenComparing(Comentarios::getDataComentario))
					.forEach(comentario -> {
						report.addTableColumn(comentario.getDataComentario().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
						report.addTableColumn(comentario.getAutor());
						report.addTableColumn(comentario.getComentario());
					});
			report.closeTable();
		}

		report.closeDocument();

		return report.getByteArrayInputStream();

	}

	public Optional<Doc> getFile(Long fileId) {
	  return docRepository.findById(fileId);
  }
  public List<Doc> getFiles(){
	  return docRepository.findAll();
  }
}
