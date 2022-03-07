package digital.refeicao.models.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Conta", schema = "login")
@Data
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prk;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conta", orphanRemoval = true)
    private List<Usuario> usuarios;

    @Column
    private String nome;


}
