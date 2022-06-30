package com.uri.gerenciadortcc.gerenciadortccApi.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.cursoObject;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Curso;
import com.uri.gerenciadortcc.gerenciadortccApi.service.CursoService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/cross")
public class crossOriginController {
	@Autowired
	private CursoService cursoservice;

	
	
}
