package com.uri.gerenciadortcc.gerenciadortccApi.service;

import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.BibliotecaObject;
import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.CursoBibliotecaObject;
import com.uri.gerenciadortcc.gerenciadortccApi.dto.BibliotecaDTO;

import java.util.List;

public interface BibliotecaService {

    public BibliotecaDTO addBiblioteca(BibliotecaObject bibliotecaObject);

    public BibliotecaDTO getBiblioteca(Long bibliotecaId);

    public List<BibliotecaDTO> getBibliotecasPorCurso(CursoBibliotecaObject cursoBibliotecaObject);

    public List<BibliotecaDTO> getBibliotecasProTitulo(String tituloTCC);

    public BibliotecaDTO atualizaDTO(Long bibliotecaId, BibliotecaObject bibliotecaObject);

    public void deleteBiblioteca(Long bilbiotecaId);

}
