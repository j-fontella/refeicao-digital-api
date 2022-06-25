package digital.refeicao.dtos.response.login;

import digital.refeicao.domains.Cargo;
import digital.refeicao.models.endereco.Endereco;
import digital.refeicao.models.login.Conta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode()
public class UsuarioCompletoResponseDTO {
    private Long prk;
    private String nome;
    private Endereco endereco;
    private String doc_registro;
    private String email;
    private Cargo cargo;
}
