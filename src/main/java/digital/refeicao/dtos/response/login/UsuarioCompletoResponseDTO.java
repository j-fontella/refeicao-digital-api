package digital.refeicao.dtos.response.login;

import digital.refeicao.domains.Cargo;
import digital.refeicao.models.login.Endereco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode()
public class UsuarioCompletoResponseDTO {
    private Long prk;
    private String nome;
    private Endereco endereco;
    private String docRegistroString;
    private String email;
    private Cargo cargo;
}
