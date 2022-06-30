package com.uri.gerenciadortcc.gerenciadortccApi.service;

import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.NotificacaoObject;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.DataOrientacao;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Orientacao;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.Professor;
import com.uri.gerenciadortcc.gerenciadortccApi.model.entity.TCC;

public interface NotificacaoService {
	
	void salvarNotificacaoNovoUsuarioProfessor(Long idProfessor);

	void salvarNotificacaoNovoUsuarioAluno(Long idAluno);

	void salvarNotificacaoOrientacao(Professor professor, TCC tcc);

	void salvarNotificacaoDataOrientacao(Orientacao orientacao, DataOrientacao dataOrientacao);

	void atualizaNotificacao(Long idNotificacao, NotificacaoObject notificacaoObject);

}
