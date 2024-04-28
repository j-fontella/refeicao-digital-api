package digital.refeicao.models.login;


import digital.refeicao.domains.Cargo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static digital.refeicao.domains.Erros.USUARIO_JA_CADASTRADO;
import static digital.refeicao.domains.Erros.USUARIO_NAO_ENCONTRADO;
import static javax.persistence.EnumType.STRING;

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
    @JoinColumn(name = "frkendereco", referencedColumnName = "prk")
    private Endereco endereco;

    @Column(columnDefinition = "varchar(14)")
    private String docRegistro;

    @Column(columnDefinition = "varchar(150)")
    private String email;

    @Column
    private String senha;

    @Enumerated(STRING)
    private Cargo cargo;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="frkconta", referencedColumnName = "prk")
    private Conta conta;



}
