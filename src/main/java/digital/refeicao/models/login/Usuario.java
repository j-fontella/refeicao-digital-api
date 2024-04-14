package digital.refeicao.models.login;


import digital.refeicao.domains.Cargo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Usuario", schema = "login")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prk;

    @Column(columnDefinition = "varchar(100)")
    private String nome;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "frk_endereco", referencedColumnName = "prk")
    private Endereco endereco;

    @Column(columnDefinition = "varchar(14)")
    private String docRegistro;

    @Column(columnDefinition = "varchar(150)")
    private String email;

    @Column
    private String senha;

    @Enumerated(EnumType.ORDINAL)
    private Cargo cargo;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="frk_conta", referencedColumnName = "prk")
    private Conta conta;


}
