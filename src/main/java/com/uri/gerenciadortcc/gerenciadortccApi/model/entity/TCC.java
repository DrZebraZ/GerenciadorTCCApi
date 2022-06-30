package com.uri.gerenciadortcc.gerenciadortccApi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "TCC", schema="mydb")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TCC {

    @Id
    @Column(name = "ID_TCC")
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long idTCC;

    @Column(name = "TITULO")
    private String titulo;

    @Column(name = "DESCRICAO")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "PROFESSOR_ID")
    private Professor orientador;

    @OneToOne
    @JoinColumn(name = "ALUNO_ID")
    private Aluno aluno;

    @OneToOne
    @JoinColumn(name = "DOC_ID")
    private Doc arquivo;

    @OneToOne
    @JoinColumn(name = "NOTIFICACAO_ID")
    private Notificacao notificacao;

}
