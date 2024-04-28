package digital.refeicao.dtos.response.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode()
public class UnidadeResponseDTO {
    private Long prk;
    private String nome;
}
