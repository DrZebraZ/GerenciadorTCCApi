package com.uri.gerenciadortcc.gerenciadortccApi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "BIBLIOTECA", schema="mydb")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Biblioteca {

    @Id
    @Column(name = "ID")
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column(name = "NOME_ALUNO")
    private String nomeAluno;

    @Column(name = "NOME_ORIENTADOR")
    private String nomeOrientador;

    @Column(name = "NOME_CURSO")
    private String nomeCurso;

    @Column(name = "TITULO_TCC")
    private String tituloTCC;

    @Column(name = "DESCRICAO_TCC")
    private String descricaoTCC;

    @OneToOne
    @JoinColumn(name = "DOC_ID")
    private Doc arquivo;
}
