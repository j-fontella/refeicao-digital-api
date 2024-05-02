package digital.refeicao.repositorys.produto;

import digital.refeicao.models.produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findByDescricao(String descricao);
}
