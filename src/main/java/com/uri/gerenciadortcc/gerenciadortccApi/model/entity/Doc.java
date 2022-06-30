package com.uri.gerenciadortcc.gerenciadortccApi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="DOC")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doc {

	public Doc(String docName, String docType, byte[] data) {
		super();
		this.docName = docName;
		this.docType = docType;
		this.data = data;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "DOC_NAME")
	private String docName;

	@Column(name = "DOC_TYPE")
	private String docType;
	
	@Lob
	private byte[] data;

	@OneToOne(mappedBy = "arquivo")
	private TCC tcc;

	@OneToOne(mappedBy = "arquivo")
	private Aluno aluno;

	@OneToOne(mappedBy = "arquivo")
	private Professor professor;

}
