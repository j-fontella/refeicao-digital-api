package digital.refeicao.controllers.login;

import digital.refeicao.dtos.request.login.UsuarioLoginRequestDTO;
import digital.refeicao.dtos.request.login.UsuarioRequestDTO;
import digital.refeicao.services.login.LoginService;
import digital.refeicao.services.login.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    TokenService tokenService;

    @Autowired
    LoginService loginService;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@RequestBody @Valid UsuarioRequestDTO usuario) {
        return loginService.registrarUsuario(usuario);
    }

    @PostMapping("/on")
    public ResponseEntity<?> registrarUsuario(@RequestBody @Valid UsuarioLoginRequestDTO usuario) {
        return loginService.on(usuario);
    }

}
