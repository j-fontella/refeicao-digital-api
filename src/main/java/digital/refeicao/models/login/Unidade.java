package digital.refeicao.models.login;

import digital.refeicao.models.produto.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "unidade", schema = "login")
@Data
public class Unidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prk;

    @Column
    private String nome;

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="frkconta", referencedColumnName = "prk")
    private Conta conta;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unidade", orphanRemoval = true)
    private List<Produto> produtos;

    @Column
    private boolean matriz;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "frkunidadematriz", referencedColumnName = "prk")
    private Unidade unidadeMatriz;


}
