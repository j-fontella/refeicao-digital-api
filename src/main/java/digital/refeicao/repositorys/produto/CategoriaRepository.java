package digital.refeicao.repositorys.produto;

import digital.refeicao.models.login.Unidade;
import digital.refeicao.models.produto.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findByDescricao(String descricao);

    List<Categoria> findByUnidade(Unidade unidade);
}
