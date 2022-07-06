package com.uri.gerenciadortcc.gerenciadortccApi.model.entity;

import com.uri.gerenciadortcc.gerenciadortccApi.model.enums.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @Column(name = "COMENTARIO")
    private String comentario;

    @Column(name="AUTOR")
    @Enumerated(value = EnumType.STRING)
    private TipoUsuario autor;

    @Column(name = "DATA_COMENTARIO")
    private LocalDateTime dataComentario;

    @ManyToOne
    @JoinColumn(name = "ORIENTACAO_ID")
    private Orientacao orientacao;

}

