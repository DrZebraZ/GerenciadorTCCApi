package com.uri.gerenciadortcc.gerenciadortccApi.service;

import java.util.ArrayList;
import java.util.List;

import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.cursoObject;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.CursoReturnDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Curso;

public interface CursoService {
	
	ArrayList<CursoReturnDTO> procuraCursos();

	Curso addCurso(Curso curso);
	
	
	
}
