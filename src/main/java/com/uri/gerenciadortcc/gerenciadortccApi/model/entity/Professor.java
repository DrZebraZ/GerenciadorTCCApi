package com.uri.gerenciadortcc.gerenciadortccApi.model.entity;

import javax.persistence.*;

import lombok.*;

import java.util.List;

@Entity
@Table (name = "PROFESSOR", schema="mydb")
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Professor extends Usuario{

	@Id
	@Column(name = "ID")
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	
	@Column(name = "TIPO_PROFESSOR")
	private int tipoProfessor;

	@Column(name = "COORDENADOR")
	private Boolean coordenador;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "professor")
	private List<Orientacao> orientacao;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "orientador")
	private List<TCC> orientacoes;

	@ManyToMany
	@JoinTable(
			name = "CURSO_PROFESSOR",
			joinColumns = @JoinColumn(name = "PROFESSOR_ID"),
			inverseJoinColumns = @JoinColumn(name = "CURSO_ID"))
	private List<Curso> cursos;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "professor")
	private List<Notificacao> notificacoes;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOC_ID")
	private Doc arquivo;

}