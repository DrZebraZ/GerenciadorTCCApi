package com.uri.gerenciadortcc.gerenciadortccApi.service;

import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Orientacao;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface EmailService {

    public void notificaDataOrientacao(Orientacao orientacao, LocalDateTime dataOrientacao);

    public void notificaDesmarcarDataOrientacao(Orientacao orientacao, LocalDateTime dataOrientacao);
}
