package digital.refeicao.dtos.request.produto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode()
public class ProdutoRequestDTO {
    private String descricao;
    private BigDecimal valor;
    private Long frkCategoria;
    private Long frkUnidade;
}
