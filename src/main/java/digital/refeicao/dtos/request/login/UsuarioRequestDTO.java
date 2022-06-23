package digital.refeicao.dtos.request.login;

import digital.refeicao.domains.Cargo;
import digital.refeicao.models.endereco.Endereco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode()
public class UsuarioRequestDTO {

    private Long prk;

    @NotBlank(message = "O nome deve ser preennchido")
    private String nome;

    @NotBlank(message = "O email deve ser preennchido")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "A senha deve ser preennchido")
    private String senha;

    @NotNull(message = "O endereço deve ser preennchido")
    @Valid
    private Endereco endereco;

    @NotNull(message = "O cargo deve ser preennchido")
    private Cargo cargo;

    @NotBlank(message = "O nome da conta deve ser preennchido")
    private String nomeConta;

    private String doc_registro;

}
