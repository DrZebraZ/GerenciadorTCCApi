package com.uri.gerenciadortcc.gerenciadortccApi.controller.objects;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
public class UsuarioObject {

    private static final String CPF_REGEX = "^[0-9]{11}$";

    private static final String DATA_REGEX = "^(1[0-2]|0[1-9])/(3[01]"
            + "|[12][0-9]|0[1-9])/[0-9]{4}$";

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*" +
            "@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final String SENHA_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$";

    private static final String NOME_REGEX = "^{4,40}$";

    private String nome;

    @Pattern(regexp = CPF_REGEX, message = "Informe o CPF corretamente")
    private String cpf;

    private LocalDate datanasc;

    @Pattern(regexp = EMAIL_REGEX, message = "Informe um email valido")
    private String email;

    @Pattern(regexp = SENHA_REGEX, message = "Informe uma senha valida")
    private String senha;

    private String tipoUsuario;

    private MultipartFile foto;

    private Long cursoId;

}
