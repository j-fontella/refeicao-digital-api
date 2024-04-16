package digital.refeicao.dtos.request.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode()
public class UsuarioRecuperacaoRequestDTO {

    @NotBlank(message = "O email é obrigatório")
    private String email;

}
