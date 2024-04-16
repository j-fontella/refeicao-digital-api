package digital.refeicao.services.login;

import digital.refeicao.domains.Erros;
import digital.refeicao.dtos.request.login.UsuarioLoginRequestDTO;
import digital.refeicao.dtos.request.login.UsuarioRecuperacaoRequestDTO;
import digital.refeicao.dtos.request.login.UsuarioRequestDTO;
import digital.refeicao.dtos.response.login.UsuarioCompletoResponseDTO;
import digital.refeicao.dtos.response.login.UsuarioResponseDTO;
import digital.refeicao.models.login.Token;
import digital.refeicao.models.login.Usuario;
import digital.refeicao.models.requisicao.Erro;
import digital.refeicao.repositorys.login.ContaRepository;
import digital.refeicao.repositorys.login.UsuarioRepository;
import digital.refeicao.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Optional;

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
        String corpoHtml = "<html><body>"
                + "<h1>Recuperação de Senha</h1>"
                + "<p>Olá " + usuario.getNome() + ",</p>"
                + "<p>Recebemos uma solicitação de recuperação de senha para a sua conta. Para redefinir sua senha, use o token " + token.getHash() + "</p>"
                + "<p>Se você não solicitou essa alteração, por favor, ignore este e-mail. A segurança da sua conta é importante para nós.</p>"
                + "<p>Atenciosamente,<br>Equipe de Suporte</p>"
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
