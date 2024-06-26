package digital.refeicao.models.produto;

import digital.refeicao.models.login.Unidade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "produto", schema = "produto")
@Data
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prk;

    @Column
    private String descricao;

    @Column
    private BigDecimal valor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "frkcategoria", referencedColumnName = "prk")
    private Categoria categoria;

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="frkunidade", referencedColumnName = "prk")
    private Unidade unidade;

}
