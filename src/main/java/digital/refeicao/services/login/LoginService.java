package digital.refeicao.services.login;

import digital.refeicao.domains.Cargo;
import digital.refeicao.domains.Erros;
import digital.refeicao.dtos.request.login.UsuarioLoginRequestDTO;
import digital.refeicao.dtos.request.login.UsuarioRequestDTO;
import digital.refeicao.dtos.response.login.UsuarioResponseDTO;
import digital.refeicao.models.login.Conta;
import digital.refeicao.models.login.Token;
import digital.refeicao.models.login.Usuario;
import digital.refeicao.models.requisicao.Erro;
import digital.refeicao.repositorys.login.ContaRepository;
import digital.refeicao.repositorys.login.UsuarioRepository;
import digital.refeicao.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    ContaRepository contaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TokenService tokenService;


    public ResponseEntity<?> registrarUsuario(UsuarioRequestDTO usuario) {
        Optional<Usuario> u = usuarioRepository.findByEmail(usuario.getEmail());
        if (u.isPresent()) {
            Erro erro = Utils.gerarErro(Erros.USUARIO_JA_CADASTRADO.getDescricao());
            return ResponseEntity.badRequest().body(erro);
        }
        Usuario novoUsuario = Utils.converterUsuarioRequest(usuario);
        usuarioRepository.save(novoUsuario);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> on(UsuarioLoginRequestDTO usuario) {
        Optional<Usuario> u = usuarioRepository.findByEmail(usuario.getEmail());
        if (u.isEmpty()) {
            Erro erro = Utils.gerarErro(Erros.USUARIO_NAO_ENCONTRADO.getDescricao());
            return ResponseEntity.badRequest().body(erro);
        }
        Usuario usuarioConectando = u.get();
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        if(!bc.matches(usuario.getSenha(), usuarioConectando.getSenha())){
            Erro erro = Utils.gerarErro(Erros.SENHA_INCORRETA.getDescricao());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
        }
        Token token = tokenService.gerarToken(usuarioConectando.getPrk());
        UsuarioResponseDTO response = new UsuarioResponseDTO(usuarioConectando.getPrk(), usuarioConectando.getNome(), token.getHash());
        return ResponseEntity.ok().body(response);
    }
}
