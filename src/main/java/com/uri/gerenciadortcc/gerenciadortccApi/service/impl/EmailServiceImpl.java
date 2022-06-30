package com.uri.gerenciadortcc.gerenciadortccApi.service.impl;

import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Orientacao;
import com.uri.gerenciadortcc.gerenciadortccApi.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void notificaDataOrientacao(Orientacao orientacao, LocalDateTime dataOrientacao) {
        try {
            MimeMessage mailAluno = mailSender.createMimeMessage();

            String localDateTimeFormatado = dataOrientacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

            MimeMessageHelper helperAluno = new MimeMessageHelper( mailAluno );
            helperAluno.setTo( orientacao.getAluno().getEmail() );
            helperAluno.setSubject( "Nova data de Orientação" );
            helperAluno.setText("Orientação marcada para o dia" + localDateTimeFormatado + " com o professor " + orientacao.getProfessor().getNome(), true);
            mailSender.send(mailAluno);

            MimeMessage mailProfessor = mailSender.createMimeMessage();

            MimeMessageHelper helperProfessor = new MimeMessageHelper( mailProfessor );
            helperProfessor.setTo( orientacao.getProfessor().getEmail() );
            helperProfessor.setSubject( "Nova data de Orientação" );
            helperProfessor.setText("Orientação marcada para o dia" + localDateTimeFormatado + " com o aluno " + orientacao.getAluno().getNome(), true);
            mailSender.send(mailAluno);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notificaDesmarcarDataOrientacao(Orientacao orientacao, LocalDateTime dataOrientacao) {
        try {
            MimeMessage mailAluno = mailSender.createMimeMessage();

            String localDateTimeFormatado = dataOrientacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

            MimeMessageHelper helperAluno = new MimeMessageHelper( mailAluno );
            helperAluno.setTo( orientacao.getAluno().getEmail() );
            helperAluno.setSubject( "Nova data de Orientação" );
            helperAluno.setText("Orientação desmarcada para o dia" + localDateTimeFormatado + " com o professor " + orientacao.getProfessor().getNome(), true);
            mailSender.send(mailAluno);

            MimeMessage mailProfessor = mailSender.createMimeMessage();

            MimeMessageHelper helperProfessor = new MimeMessageHelper( mailProfessor );
            helperProfessor.setTo( orientacao.getProfessor().getEmail() );
            helperProfessor.setSubject( "Nova data de Orientação" );
            helperProfessor.setText("Orientação desmarcada para o dia" + localDateTimeFormatado + " com o aluno " + orientacao.getAluno().getNome(), true);
            mailSender.send(mailAluno);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
