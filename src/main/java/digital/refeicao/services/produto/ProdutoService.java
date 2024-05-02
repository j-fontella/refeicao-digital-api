package digital.refeicao.services.produto;

import digital.refeicao.dtos.request.produto.ProdutoRequestDTO;
import digital.refeicao.exceptions.NegocioException;
import digital.refeicao.models.login.Unidade;
import digital.refeicao.models.produto.Produto;
import digital.refeicao.repositorys.produto.ProdutoRepository;
import digital.refeicao.services.login.LoginService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static digital.refeicao.domains.Erros.PRODUTO_JA_CADASTRADO;
import static digital.refeicao.domains.Erros.PRODUTO_NAO_ENCONTRADO;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaService categoriaService;
    private final LoginService loginService;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository, CategoriaService categoriaService, LoginService loginService) {
        this.produtoRepository = produtoRepository;
        this.categoriaService = categoriaService;
        this.loginService = loginService;
    }

    public ResponseEntity<?> registrarProduto(ProdutoRequestDTO produtoRequestDTO) {
        findProduto(produtoRequestDTO.getDescricao(), false);
        produtoRepository.save(converterProdutoRequest(produtoRequestDTO));
        return ResponseEntity.ok().build();
    }

    private Produto converterProdutoRequest(ProdutoRequestDTO produtoRequestDTO) {
        Produto produto = new ModelMapper().map(produtoRequestDTO, Produto.class);
        produto.setCategoria(categoriaService.findCategoria(produtoRequestDTO.getFrkCategoria(), true));
        produto.setUnidade(loginService.findUnidade(produtoRequestDTO.getFrkUnidade(), true));
        return produto;
    }


    public Produto findProduto(Long prk, boolean existente){
        return validarProduto(produtoRepository.findById(prk), existente);
    }

    public Produto findProduto(String descricao, boolean existente){
        return validarProduto(produtoRepository.findByDescricao(descricao), existente);
    }

    private Produto validarProduto(Optional<Produto> p, boolean existente) {
        if (p.isPresent() != existente) {
            throw new NegocioException(existente ? PRODUTO_NAO_ENCONTRADO : PRODUTO_JA_CADASTRADO);
        }
        return existente ? p.get() : null;
    }

    public ResponseEntity<?> getCategorias(Long frkUnidade) {
        Unidade unidade = loginService.findUnidade(frkUnidade, true);
        return ResponseEntity.ok().body(categoriaService.findByUnidade(unidade));
    }
}
