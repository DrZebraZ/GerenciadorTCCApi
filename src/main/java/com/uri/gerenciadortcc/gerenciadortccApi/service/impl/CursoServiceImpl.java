package com.uri.gerenciadortcc.gerenciadortccApi.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uri.gerenciadortcc.gerenciadortccApi.dto.AlunoLoginDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.CursoReturnDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.TCCAlunoDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Aluno;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Curso;
import com.uri.gerenciadortcc.gerenciadortccApi.model.repository.CursoRepository;
import com.uri.gerenciadortcc.gerenciadortccApi.service.CursoService;

@Service
public class CursoServiceImpl implements CursoService{

	@Autowired
	private CursoRepository repository;
	
	@Override
	public CursoReturnDTO addCurso(Curso curso) {
		return parserCursoReturnDTO(repository.save(curso));
	}
	
	@Override
	public ArrayList<CursoReturnDTO> procuraCursos() {
		return parserListCursoReturnDTO(repository.retornaCursos());
	}
	
	private ArrayList<CursoReturnDTO> parserListCursoReturnDTO(ArrayList<Curso> lista) {
		ArrayList<CursoReturnDTO> listaCursos = new ArrayList<CursoReturnDTO>();
		int tamanho = lista.size();
		for (int i=0; i < tamanho ; i++) {
			CursoReturnDTO cursoRetDT = new CursoReturnDTO();
	        cursoRetDT.setIdCurso(lista.get(i).getIdCurso());
	        cursoRetDT.setAreacurso(lista.get(i).getAreacurso());
	        cursoRetDT.setNome(lista.get(i).getNome());
	        listaCursos.add(cursoRetDT);
		}       
        return listaCursos;
    }

	private CursoReturnDTO parserCursoReturnDTO(Curso curso) {


		CursoReturnDTO cursoRetDT = new CursoReturnDTO();
		cursoRetDT.setIdCurso(curso.getIdCurso());
		cursoRetDT.setAreacurso(curso.getAreacurso());
		cursoRetDT.setNome(curso.getNome());

		return cursoRetDT;



	}
}
