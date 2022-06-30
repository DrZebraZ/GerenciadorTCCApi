package com.uri.gerenciadortcc.gerenciadortccApi.model.entity;

import java.time.LocalDate;

import javax.persistence.*;

import com.uri.gerenciadortcc.gerenciadortccApi.model.enums.TipoUsuario;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public class Usuario {

	@Column(name = "TIPO_USUARIO")
	@Enumerated(EnumType.STRING)
	private TipoUsuario tipoUsuario;

	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "CPF")
	private String cpf;
	
	@Column(name = "DATA_NASC")
	private LocalDate datanasc;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "SENHA")
	private String senha;
	
	@Column(name = "VERIFICADO")
	private Boolean verificado;
	
}
