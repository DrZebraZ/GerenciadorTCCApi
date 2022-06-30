package com.uri.gerenciadortcc.gerenciadortccApi.model.entity;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import javax.persistence.*;

import com.uri.gerenciadortcc.gerenciadortccApi.model.enums.TipoNotificacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table (name = "NOTIFICACAO", schema="mydb")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notificacao {
	
	@Id
	@Column(name = "ID_NOTIFICACAO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idNotificacao;
	
	@Column(name="DATA_NOTIFICACAO")
	private LocalDate dataNotificacao;
	
	@Column(name="TIPO_NOTIFICACAO")
	@Enumerated(value = EnumType.STRING)
	private TipoNotificacao tipoNotificacao;

	@ManyToOne
	@JoinColumn(name = "PROFESSOR_ID")
	private Professor professor;

	@ManyToOne
	@JoinColumn(name = "ALUNO_ID")
	private Aluno aluno;

	@OneToOne(mappedBy = "notificacao")
	private TCC tcc;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORIENTACAO_ID")
	private Orientacao orientacao;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DATA_ORIENTACAO_ID")
	private DataOrientacao dataOrientacao;
	
	@Column(name="confirmada")
	private Boolean confirmada;
	
	@Column(name="descartada")
	private Boolean descartada;

	@UpdateTimestamp
	@Column(name = "DATA_CONFIRMACAO")
	private LocalDate dataConfirmacao;
}

