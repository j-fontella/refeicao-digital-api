package digital.refeicao.services.login;

import digital.refeicao.domains.Cargo;
import digital.refeicao.dtos.request.login.UsuarioAlteracaoRequestDTO;
import digital.refeicao.dtos.request.login.UsuarioLoginRequestDTO;
import digital.refeicao.dtos.request.login.UsuarioRecuperacaoRequestDTO;
import digital.refeicao.dtos.request.login.UsuarioRequestDTO;
import digital.refeicao.dtos.response.login.UsuarioCompletoResponseDTO;
import digital.refeicao.dtos.response.login.UsuarioResponseDTO;
import digital.refeicao.exceptions.NegocioException;
import digital.refeicao.models.login.Conta;
import digital.refeicao.models.login.Token;
import digital.refeicao.models.login.Usuario;
import digital.refeicao.repositorys.login.UsuarioRepository;
import digital.refeicao.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Optional;

import static digital.refeicao.domains.Erros.*;
import static digital.refeicao.utils.Utils.*;

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
        findUsuario(usuario.getPrk(), false);
        usuarioRepository.save(converterUsuarioRequest(usuario));
        return ResponseEntity.ok().build();
    }

    private Usuario converterUsuarioRequest(UsuarioRequestDTO u){
        Usuario usuario = new ModelMapper().map(u, Usuario.class);
        Conta novaConta = new Conta();
        novaConta.setNome(u.getNomeConta());
        usuario.setConta(novaConta);
        usuario.setCargo(Cargo.PROPRIETARIO);
        usuario.setSenha(Utils.criptografarBCrypt(u.getSenha()));
        usuario.setDocRegistro(u.getDocRegistro());
        return usuario;
    }

    public ResponseEntity<?> on(UsuarioLoginRequestDTO usuario) {
        Usuario usuarioConectando = findUsuario(usuario.getEmail(), true);
        validarBCrypt(usuario.getSenha(), usuarioConectando.getSenha(), SENHA_INCORRETA);
        Token token = tokenService.gerarToken(usuarioConectando);
        return ResponseEntity.ok().body(new UsuarioResponseDTO(usuarioConectando.getPrk(), usuarioConectando.getNome(), base64Encode(usuarioConectando.getPrk()+":"+token.getHash())));
    }

    public ResponseEntity<?> getUsuarioPorPrk(Long prk) {
        return ResponseEntity.ok().body(converterUsuarioCompletoResponseDTO(findUsuario(prk, true)));
    }

    private UsuarioCompletoResponseDTO converterUsuarioCompletoResponseDTO(Usuario u){
        return new ModelMapper().map(u, UsuarioCompletoResponseDTO.class);
    }

    public ResponseEntity<?> alterarSenha(UsuarioAlteracaoRequestDTO usuarioAlteracaoRequestDTO) {
        Usuario usuario = findUsuario(usuarioAlteracaoRequestDTO.getEmail(), true);
        tokenService.validarToken(usuarioAlteracaoRequestDTO.getCodigo(), usuario.getPrk());
        usuario.setSenha(criptografarBCrypt(usuarioAlteracaoRequestDTO.getSenha()));
        usuarioRepository.save(usuario);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> recuperarSenha(UsuarioRecuperacaoRequestDTO usuarioRecuperacaoRequestDTO) {
        return enviarEmailRecuperacaoSenha(findUsuario(usuarioRecuperacaoRequestDTO.getEmail(), true));
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


    public Usuario findUsuario(Long prk, boolean existente){
        return validarUsuario(usuarioRepository.findById(prk), existente);
    }

    public Usuario findUsuario(String email, boolean existente){
        return validarUsuario(usuarioRepository.findByEmail(email), existente);
    }

    private Usuario validarUsuario(Optional<Usuario> u, boolean existente) {
        if (u.isPresent() != existente) {
            throw new NegocioException(existente ? USUARIO_NAO_ENCONTRADO : USUARIO_JA_CADASTRADO);
        }
        return existente ? u.get() : null;
    }
}
