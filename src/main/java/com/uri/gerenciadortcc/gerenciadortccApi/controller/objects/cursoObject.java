package com.uri.gerenciadortcc.gerenciadortccApi.controller.objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class cursoObject {

	private String nomecurso;
	private Long idcurso;
}
