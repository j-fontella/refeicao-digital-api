package digital.refeicao.controllers.login;

import digital.refeicao.annotations.token.ValidateToken;
import digital.refeicao.dtos.request.login.UsuarioAlteracaoRequestDTO;
import digital.refeicao.dtos.request.login.UsuarioLoginRequestDTO;
import digital.refeicao.dtos.request.login.UsuarioRecuperacaoRequestDTO;
import digital.refeicao.dtos.request.login.UsuarioRequestDTO;
import digital.refeicao.services.login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/login")
public class LoginController {


    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@RequestBody @Valid UsuarioRequestDTO usuario) {
        return loginService.registrarUsuario(usuario);
    }
    @PostMapping("/on")
    public ResponseEntity<?> on(@RequestBody @Valid UsuarioLoginRequestDTO usuario) {
        return loginService.on(usuario);
    }

    @PostMapping("/recuperar")
    public ResponseEntity<?> recuperarSenha(@RequestBody @Valid UsuarioRecuperacaoRequestDTO usuario) {
        return loginService.recuperarSenha(usuario);
    }

    @PostMapping("/alterar")
    public ResponseEntity<?> alterarSenha(@RequestBody @Valid UsuarioAlteracaoRequestDTO usuario) {
        return loginService.alterarSenha(usuario);
    }
    @ValidateToken
    @GetMapping("/usuario/{prk}")
    public ResponseEntity<?> getUsuarioPorPrk(@PathVariable Long prk) {
        return loginService.getUsuarioPorPrk(prk);
    }

}
