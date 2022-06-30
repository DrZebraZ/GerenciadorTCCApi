package com.uri.gerenciadortcc.gerenciadortccApi.controller.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class loginObject {

	private String email;
	private String senha;

}
