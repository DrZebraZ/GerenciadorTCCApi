package com.uri.gerenciadortcc.gerenciadortccApi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "ORIENTACAO", schema="mydb")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orientacao {

    @Id
    @Column(name = "ID_ORIENTACAO")
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long idOrientacao;

    @Column(name = "TITULO_TCC")
    private String tituloTCC;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ALUNO_ID")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "PROFESSOR_ID")
    private Professor professor;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orientacao")
    private List<DataOrientacao> datasOrientacoes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orientacao")
    private List<Comentarios> comentarios;

    @OneToOne(mappedBy = "orientacao")
    private Notificacao notificacao;

}
