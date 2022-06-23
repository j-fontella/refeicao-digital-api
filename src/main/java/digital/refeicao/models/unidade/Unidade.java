package digital.refeicao.models.unidade;

import digital.refeicao.models.login.Conta;
import digital.refeicao.models.produto.Categoria;
import digital.refeicao.models.produto.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "unidade", schema = "unidade")
@Data
public class Unidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prk;

    @Column
    private String nome;

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="frk_conta", referencedColumnName = "prk")
    private Conta conta;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unidade", orphanRemoval = true)
    private List<Produto> produtos;

    @Column
    private boolean matriz;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "frk_unidade_matriz", referencedColumnName = "prk")
    private Unidade unidadeMatriz;


}
