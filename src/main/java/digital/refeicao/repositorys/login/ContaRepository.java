package digital.refeicao.repositorys.login;

import digital.refeicao.models.login.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Long> {
}
