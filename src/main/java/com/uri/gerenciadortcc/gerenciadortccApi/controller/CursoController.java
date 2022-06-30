package com.uri.gerenciadortcc.gerenciadortccApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.cursoObject;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.CursoReturnDTO;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Curso;
import com.uri.gerenciadortcc.gerenciadortccApi.service.CursoService;

import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/curso")
public class CursoController {
	
	@Autowired
	private CursoService cursoservice;
		
	@PostMapping("/add")
	public String add(@RequestBody Curso curso) {
		cursoservice.addCurso(curso);
		return "Curso Adicionado com Sucesso";
	}

	@GetMapping("/getCursos")
	public ArrayList<CursoReturnDTO> getall(){
		return cursoservice.procuraCursos();
	}
}
