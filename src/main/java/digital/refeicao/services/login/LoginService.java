package digital.refeicao.services.login;

import digital.refeicao.domains.Erros;
import digital.refeicao.dtos.request.login.UsuarioAlteracaoRequestDTO;
import digital.refeicao.dtos.request.login.UsuarioLoginRequestDTO;
import digital.refeicao.dtos.request.login.UsuarioRecuperacaoRequestDTO;
import digital.refeicao.dtos.request.login.UsuarioRequestDTO;
import digital.refeicao.dtos.response.login.UsuarioCompletoResponseDTO;
import digital.refeicao.dtos.response.login.UsuarioResponseDTO;
import digital.refeicao.models.login.Token;
import digital.refeicao.models.login.Usuario;
import digital.refeicao.models.requisicao.Erro;
import digital.refeicao.repositorys.login.UsuarioRepository;
import digital.refeicao.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Optional;

import static digital.refeicao.utils.Utils.encriptarStringBCrypt;

@Service
public class LoginService {



    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;
    private final JavaMailSender javaMailSender;
    @Autowired
    public LoginService(UsuarioRepository usuarioRepository, TokenService tokenService, JavaMailSender javaMailSender) {
        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;
        this.javaMailSender = javaMailSender;
    }

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
        Token token = tokenService.gerarToken(usuarioConectando);
        UsuarioResponseDTO response = new UsuarioResponseDTO(usuarioConectando.getPrk(), usuarioConectando.getNome(), token.getHash());
        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity<?> getUsuarioPorPrk(Long prk, String token) {
        tokenService.validarToken(token,prk);
        Optional<Usuario> u = usuarioRepository.findById(prk);
        if (u.isEmpty()) {
            Erro erro = Utils.gerarErro(Erros.USUARIO_NAO_ENCONTRADO.getDescricao());
            return ResponseEntity.badRequest().body(erro);
        }
        UsuarioCompletoResponseDTO response = Utils.converterUsuarioCompletoResponseDTO(u.get());
        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity<?> alterarSenha(UsuarioAlteracaoRequestDTO usuarioAlteracaoRequestDTO) {
        Optional<Usuario> u = usuarioRepository.findByEmail(usuarioAlteracaoRequestDTO.getEmail());
        if (u.isEmpty()) {
            Erro erro = Utils.gerarErro(Erros.USUARIO_NAO_ENCONTRADO.getDescricao());
            return ResponseEntity.badRequest().body(erro);
        }
        Usuario usuario = u.get();
        tokenService.validarToken(usuarioAlteracaoRequestDTO.getCodigo(), usuario.getPrk());
        usuario.setSenha(encriptarStringBCrypt(usuarioAlteracaoRequestDTO.getSenha()));
        usuarioRepository.save(usuario);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> recuperarSenha(UsuarioRecuperacaoRequestDTO usuarioRecuperacaoRequestDTO) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(usuarioRecuperacaoRequestDTO.getEmail());
        if (usuario.isEmpty()) {
            Erro erro = Utils.gerarErro(Erros.USUARIO_NAO_ENCONTRADO.getDescricao());
            return ResponseEntity.badRequest().body(erro);
        }
        return enviarEmailRecuperacaoSenha(usuario.get());
    }

    public ResponseEntity<?> enviarEmailRecuperacaoSenha(Usuario usuario) {
        Token token = tokenService.gerarTokenRecuperacao(usuario);
        String para = usuario.getEmail();
        String assunto = "Recuperação de Senha";
        String corpoHtml = "<html><head><style>"
                + "body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }"
                + ".container { width: 80%; margin: 0 auto; padding: 40px 20px; background-color: #fff; border-radius: 10px; box-shadow: 0px 0px 20px 0px rgba(0, 0, 0, 0.1); text-align: center; }"
                + "h1 { color: #333; margin-bottom: 20px; }"
                + "p { color: #666; margin-bottom: 20px; }"
                + ".button { display: inline-block; padding: 10px 20px; background-color: #4169E1; color: #fff; text-decoration: none; border-radius: 5px; }"
                + ".button:hover { background-color: #3458b5; }"
                + ".footer { margin-top: 20px; color: #999; }"
                + "</style></head>"
                + "<body>"
                + "<div class='container'>"
                + "<h1>Recuperação de Senha</h1>"
                + "<p>Olá, " + usuario.getNome() + ",</p>"
                + "<p>Recebemos uma solicitação de recuperação de senha para a sua conta. Para redefinir sua senha, utilize o código: " + " <b>" + token.getHash() + "</b></p>"
                + "<p style='margin-top: 20px;'>Se você não solicitou uma alteração de senha, pode ignorar este e-mail com segurança.</p>"
                + "<p class='footer'>Atenciosamente,<br>Equipe de Suporte</p>"
                + "</div>"
                + "</body></html>";




        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(para);
            helper.setSubject(assunto);
            helper.setText(corpoHtml, true);
            javaMailSender.send(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao recuperar senha");

        }
        return ResponseEntity.ok().build();
    }
}
