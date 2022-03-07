package digital.refeicao.controllers.unidade;


import digital.refeicao.instances.UsuarioLogado;
import digital.refeicao.models.login.Usuario;
import digital.refeicao.services.login.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/unidade")
public class UnidadeController {

    @Autowired
    TokenService tokenService;

    @GetMapping("/yay")
    public ResponseEntity<?> yay(@RequestHeader(name = "token") String token, @RequestParam Long prk){
        tokenService.validarToken(token,prk);
        return ResponseEntity.ok("YAY");
    }

}
