package digital.refeicao.models.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "UsuarioUnidade", schema = "login")
@Data
public class UsuarioUnidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "frkusuario", referencedColumnName = "prk")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "frkunidade", referencedColumnName = "prk")
    private Unidade unidade;

}
