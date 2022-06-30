package com.uri.gerenciadortcc.gerenciadortccApi.model.entity;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table (name = "CURSO", schema="mydb")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Curso {

	@Id
	@Column(name = "ID_CURSO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCurso;
	
	@Column(name = "NOME_CURSO")
	private String nome;
	
	@Column(name = "AREA_CURSO")
	private String areacurso;

	@ManyToMany(mappedBy = "cursos")
	private List<Professor> professores;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "curso")
	private List<Aluno> alunos;
}
