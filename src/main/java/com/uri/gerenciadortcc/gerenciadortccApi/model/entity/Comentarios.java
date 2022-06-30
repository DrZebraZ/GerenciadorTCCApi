package com.uri.gerenciadortcc.gerenciadortccApi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "comentarios", schema="mydb")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comentarios {

    @Id
    @Column(name = "ID_COMENTARIO")
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long idComentario;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "COMENTARIO")
    private String comentario;

    @Column(name = "DATA_COMENTARIO")
    private LocalDate dataComentario;

    @ManyToOne
    @JoinColumn(name = "ORIENTACAO_ID")
    private Orientacao orientacao;

}

