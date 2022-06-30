package com.uri.gerenciadortcc.gerenciadortccApi.service.impl;

import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Orientacao;
import com.uri.gerenciadortcc.gerenciadortccApi.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void notificaDataOrientacao(Orientacao orientacao, LocalDateTime dataOrientacao) {
        try {

            SimpleMailMessage mailAluno = new SimpleMailMessage();

            String localDateTimeFormatado = dataOrientacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

            mailAluno.setTo(orientacao.getAluno().getEmail());
            mailAluno.setSubject("Nova data de Orientação");
            mailAluno.setText("Orientação marcada para o dia " + localDateTimeFormatado + " com o professor " + orientacao.getProfessor().getNome());
            mailSender.send(mailAluno);

            SimpleMailMessage mailProfessor = new SimpleMailMessage();

            mailProfessor.setTo(orientacao.getProfessor().getEmail());
            mailProfessor.setSubject("Nova data de Orientação");
            mailProfessor.setText("Orientação marcada para o dia " + localDateTimeFormatado + " com o aluno " + orientacao.getAluno().getNome() +" do curso de " + orientacao.getAluno().getCurso().getNome());
            mailSender.send(mailProfessor);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notificaDesmarcarDataOrientacao(Orientacao orientacao, LocalDateTime dataOrientacao) {
        try {
            SimpleMailMessage mailAluno = new SimpleMailMessage();

            String localDateTimeFormatado = dataOrientacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

            mailAluno.setTo( "romulocado@gmail.com" );
            mailAluno.setSubject( "Desmarcar Orientação" );
            mailAluno.setText("Orientação desmarcada no dia " + localDateTimeFormatado + " com o professor " + orientacao.getProfessor().getNome());
            mailSender.send(mailAluno);

            SimpleMailMessage mailProfessor = new SimpleMailMessage();

            mailProfessor.setTo(orientacao.getProfessor().getEmail());
            mailProfessor.setSubject( "Desmarcar Orientação" );
            mailProfessor.setText("Orientação desmarcada no o dia " + localDateTimeFormatado  + " com o aluno " + orientacao.getAluno().getNome() +" do curso de " + orientacao.getAluno().getCurso().getNome());
            mailSender.send(mailProfessor);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
