package digital.refeicao.controllers.unidade;

import digital.refeicao.dtos.request.login.UsuarioRequestDTO;
import digital.refeicao.services.unidade.UnidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/unidade")
public class UnidadeController {

    private final UnidadeService unidadeService;

    @Autowired
    public UnidadeController(UnidadeService unidadeService) {
        this.unidadeService = unidadeService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuarioUnidade(@RequestBody @Valid UsuarioRequestDTO usuario) {
        return unidadeService.registrarUsuarioUnidade(usuario);
    }

}
