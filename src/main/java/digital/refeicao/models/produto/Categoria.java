package digital.refeicao.models.produto;

import digital.refeicao.models.login.Unidade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categoria", schema = "produto")
@Data
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prk;

    @Column
    private String descricao;

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="frkunidade", referencedColumnName = "prk")
    private Unidade unidade;
}
