package com.uri.gerenciadortcc.gerenciadortccApi.service.impl;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.NotificacaoObject;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.*;
import com.uri.gerenciadortcc.gerenciadortccApi.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uri.gerenciadortcc.gerenciadortccApi.model.enums.TipoNotificacao;
import com.uri.gerenciadortcc.gerenciadortccApi.service.NotificacaoService;

@Service
public class NotificacaoServiceImpl implements NotificacaoService{

	@Autowired
	private NotificacaoRepository repository;

	@Autowired
	private ProfessorRepository professorRepository;

	@Autowired
	private AlunoRepository alunoRepository;

	@Autowired
	private TCCRepository tccRepository;

	@Autowired
	private DataOrientacaoRepository dataOrientacaoRepository;

	@Autowired
	private OrientacaoRepository orientacaoRepository;

	@Override
	public void salvarNotificacaoNovoUsuarioProfessor(Long idProfessor){
		try {
			Optional<Professor> professor = professorRepository.findById(idProfessor);
			Notificacao notif = (Notificacao.builder()
				.dataNotificacao(LocalDate.now())
				.tipoNotificacao(TipoNotificacao.NOVOUSUARIO)
				.professor(professor.get()).build());
			repository.save(notif);
			if(professor.get().getNotificacoes() != null){
				List<Notificacao> notificacoes = professor.get().getNotificacoes();
				notificacoes.add(notif);
				Professor professorEntity = professor.get();
				professorEntity.setNotificacoes(notificacoes);
				professorRepository.save(professorEntity);
			}else{
				List<Notificacao> notificacoes = new ArrayList<>();
				notificacoes.add(notif);
				Professor professorEntity = professor.get();
				professorEntity.setNotificacoes(notificacoes);
				professorRepository.save(professorEntity);
			}
		}catch(Exception e){
			System.out.println("nao foi "+e);
		}
	}

	@Override
	public void salvarNotificacaoNovoUsuarioAluno(Long idAluno){
		try {
			Optional<Aluno> aluno = alunoRepository.findById(idAluno);
			Notificacao notif = (Notificacao.builder()
					.dataNotificacao(LocalDate.now())
					.tipoNotificacao(TipoNotificacao.NOVOUSUARIO)
					.aluno(aluno.get()).build());
			repository.save(notif);
			if(aluno.get().getNotificacoes() != null){
				List<Notificacao> notificacoes = aluno.get().getNotificacoes();
				notificacoes.add(notif);
				Aluno alunoEntity = aluno.get();
				alunoEntity.setNotificacoes(notificacoes);
				alunoRepository.save(alunoEntity);
			}else{
				List<Notificacao> notificacoes = new ArrayList<>();
				notificacoes.add(notif);
				Aluno alunoEntity = aluno.get();
				alunoEntity.setNotificacoes(notificacoes);
				alunoRepository.save(alunoEntity);
			}
		}catch(Exception e){
			System.out.println("nao foi "+e);
		}
	}

	@Override
	public void salvarNotificacaoOrientacao(Professor professor, TCC tcc) {
		try {
			Notificacao notif = (Notificacao.builder()
					.dataNotificacao(LocalDate.now())
					.tipoNotificacao(TipoNotificacao.ORIENTACAO)
					.professor(professor)
					.tcc(tcc)
					.build());
			repository.save(notif);
		}catch(Exception e){
			System.out.println("nao foi "+e);
		}
	}

	@Override
	public void salvarNotificacaoDataOrientacao(Orientacao orientacao, DataOrientacao dataOrientacao) {
		try {
			Notificacao notif = (Notificacao.builder()
					.dataNotificacao(LocalDate.now())
					.tipoNotificacao(TipoNotificacao.MARCAR_ORIENTACAO)
					.dataOrientacao(dataOrientacao)
					.orientacao(orientacao).build());
			repository.save(notif);
		}catch(Exception e){
			System.out.println("nao foi "+e);
		}
	}

	@Override
	public void atualizaNotificacao(Long idNotificacao, NotificacaoObject notificacaoObject) {
		Optional<Notificacao> notificacao = repository.findById(idNotificacao);

		if(notificacao.isPresent()){
			Notificacao notificacaoAtualizada = notificacao.get();
			notificacaoAtualizada.setConfirmada(notificacaoObject.getConfirmada());
			notificacaoAtualizada.setDescartada(notificacaoObject.getDescartada());
			notificacaoAtualizada.setDataConfirmacao(LocalDate.now());
			repository.save(notificacaoAtualizada);
			if(notificacaoAtualizada.getTipoNotificacao().equals(TipoNotificacao.ORIENTACAO)){
				tarefaOrientacao(notificacaoAtualizada);
			}else if(notificacaoAtualizada.getTipoNotificacao().equals(TipoNotificacao.MARCAR_ORIENTACAO)){
				tarefaMarcarOrientacao(notificacaoAtualizada);
			}
		}

	}

	private void tarefaOrientacao(Notificacao notificacaoAtualizada) {
		if(notificacaoAtualizada.getConfirmada()){
			TCC tcc = notificacaoAtualizada.getTcc();
			tcc.setOrientador(notificacaoAtualizada.getProfessor());
			tccRepository.save(tcc);
		}
	}

	private void tarefaMarcarOrientacao(Notificacao notificacaoAtualizada){
		if(notificacaoAtualizada.getDescartada()){
			dataOrientacaoRepository.deleteById(notificacaoAtualizada.getDataOrientacao().getId());
		}else if(notificacaoAtualizada.getConfirmada()){
			Orientacao orientacao = notificacaoAtualizada.getOrientacao();
			if(notificacaoAtualizada.getOrientacao().getDatasOrientacoes() != null){
				List<DataOrientacao> datasOrientacoes = orientacao.getDatasOrientacoes();
				datasOrientacoes.add(notificacaoAtualizada.getDataOrientacao());
				orientacao.setDatasOrientacoes(datasOrientacoes);
				orientacaoRepository.save(orientacao);
			}else{
				List<DataOrientacao> datasOrientacoes = new ArrayList<>();
				datasOrientacoes.add(notificacaoAtualizada.getDataOrientacao());
				orientacao.setDatasOrientacoes(datasOrientacoes);
				orientacaoRepository.save(orientacao);
			}
		}
	}


}
