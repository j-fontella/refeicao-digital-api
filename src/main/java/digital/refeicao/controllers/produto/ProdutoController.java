package digital.refeicao.controllers.produto;

import digital.refeicao.annotations.token.ValidateToken;
import digital.refeicao.dtos.request.login.UsuarioRequestDTO;
import digital.refeicao.dtos.request.produto.ProdutoRequestDTO;
import digital.refeicao.services.produto.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/produto")
public class ProdutoController {

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @ValidateToken
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarProduto(@RequestBody @Valid ProdutoRequestDTO produtoRequestDTO) {
        return produtoService.registrarProduto(produtoRequestDTO);
    }

    @ValidateToken
    @GetMapping("/categorias/{frkUnidade}")
    public ResponseEntity<?> getCategorias(Long frkUnidade) {
        return produtoService.getCategorias(frkUnidade);
    }


}
