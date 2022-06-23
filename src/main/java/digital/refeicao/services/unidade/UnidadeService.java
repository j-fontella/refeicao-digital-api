package digital.refeicao.services.unidade;

import digital.refeicao.dtos.request.login.UsuarioRequestDTO;
import digital.refeicao.services.login.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UnidadeService {

    private final LoginService loginService;

    public UnidadeService(LoginService loginService) {
        this.loginService = loginService;
    }


    public ResponseEntity<?> registrarUsuarioUnidade(UsuarioRequestDTO usuario) {
        //setar unidade
        return loginService.registrarUsuario(usuario);
    }
}
