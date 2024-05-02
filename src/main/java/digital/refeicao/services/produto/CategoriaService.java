package digital.refeicao.services.produto;

import digital.refeicao.exceptions.NegocioException;
import digital.refeicao.models.login.Unidade;
import digital.refeicao.models.produto.Categoria;
import digital.refeicao.repositorys.produto.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static digital.refeicao.domains.Erros.CATEGORIA_JA_CADASTRADA;
import static digital.refeicao.domains.Erros.CATEGORIA_NAO_ENCONTRADA;


@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }


    public Categoria findCategoria(Long prk, boolean existente){
        return validarCategoria(categoriaRepository.findById(prk), existente);
    }

    public Categoria findCategoria(String descricao, boolean existente){
        return validarCategoria(categoriaRepository.findByDescricao(descricao), existente);
    }

    public List<Categoria> findByUnidade(Unidade unidade){
        return categoriaRepository.findByUnidade(unidade);
    }

    private Categoria validarCategoria(Optional<Categoria> c, boolean existente) {
        if (c.isPresent() != existente) {
            throw new NegocioException(existente ? CATEGORIA_NAO_ENCONTRADA : CATEGORIA_JA_CADASTRADA);
        }
        return existente ? c.get() : null;
    }
    
}
