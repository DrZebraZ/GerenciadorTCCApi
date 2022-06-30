package com.uri.gerenciadortcc.gerenciadortccApi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Table(name = "DATA_ORIENTACAO", schema="mydb")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataOrientacao {

    @Id
    @Column(name = "ID")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DATA_ORIENTACAO")
    private LocalDateTime dataOrientacao;

    @ManyToOne
    @JoinColumn(name = "ORIENTACAO_ID")
    private Orientacao orientacao;

    @OneToOne(mappedBy = "dataOrientacao")
    private Notificacao notificacao;
}
