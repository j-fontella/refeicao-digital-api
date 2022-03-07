package digital.refeicao.models.login;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "Token", schema = "login")
public class Token {

    @Id
    private String hash;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "frk_usuario", referencedColumnName = "prk")
    private Usuario usuario;

    @Column
    private LocalDateTime dataExpiracao;

}
