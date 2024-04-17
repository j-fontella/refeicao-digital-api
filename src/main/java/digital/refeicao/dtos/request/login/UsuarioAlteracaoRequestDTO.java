package digital.refeicao.dtos.request.login;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode()
public class UsuarioAlteracaoRequestDTO {

    @NotBlank(message = "O email deve ser preenchido")
    @Email(message = "O email inserido é inválido")
    private String email;

    @NotBlank(message = "A senha deve ser preenchido")
    private String senha;

    @NotBlank(message = "O código deve ser preenchido")
    private String codigo;


}
