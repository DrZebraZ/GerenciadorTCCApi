package com.uri.gerenciadortcc.gerenciadortccApi.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ALUNO", schema="mydb")
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Aluno extends Usuario {

    @Id
    @Column(name = "ID")
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @OneToOne(mappedBy = "aluno")
    private TCC tcc;

    @OneToOne(mappedBy = "aluno")
    private Orientacao orientacao;

    @ManyToOne
    @JoinColumn(name = "CURSO_ID")
    private Curso curso;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aluno")
    private List<Notificacao> notificacoes;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOC_ID")
    private Doc arquivo;

}
