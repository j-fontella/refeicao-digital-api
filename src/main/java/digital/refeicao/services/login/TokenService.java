package digital.refeicao.services.login;

import digital.refeicao.domains.Erros;
import digital.refeicao.models.login.Token;
import digital.refeicao.models.login.Usuario;
import digital.refeicao.repositorys.login.TokenRepository;
import digital.refeicao.repositorys.login.UsuarioRepository;
import digital.refeicao.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void validarToken(String hash, Long prk){
        Optional<Token> tokenQuery = tokenRepository.findByFrkUsuario(prk);
        if(tokenQuery.isEmpty()){
            throw new SecurityException("Token não cadastrado.");
        }
        Token token = tokenQuery.get();
        if(!token.getHash().equals(hash)){
            throw new SecurityException("Token inválido.");
        }
        if(LocalDateTime.now().isAfter(token.getDataExpiracao())){
            throw new SecurityException("Token expirado.");
        }
    }

    public void limparToken(Long prk){
        tokenRepository.limpar(prk);
    }

    public Token gerarToken(Long prk){
        Optional<Usuario> usuario = usuarioRepository.findById(prk);
        if(usuario.isEmpty()){
            throw new SecurityException(Erros.USUARIO_NAO_ENCONTRADO.getDescricao());
        }
        limparToken(prk);
        return criarNovoToken(usuario.get());
    }

    public Token gerarToken(Usuario usuario){
        limparToken(usuario.getPrk());
        return criarNovoToken(usuario);
    }
    private Token criarNovoToken(Usuario usuario){
        Token token = getToken(usuario);
        token.setDataExpiracao(LocalDateTime.now().plusHours(2));
        token.setHash(Utils.encriptarStringBCrypt(usuario.getSenha() + usuario.getEmail() + LocalDateTime.now()));
        return tokenRepository.save(token);
    }

    public Token gerarTokenRecuperacao(Usuario usuario){
        limparToken(usuario.getPrk());
        Token token = getToken(usuario);
        token.setDataExpiracao(LocalDateTime.now().plusMinutes(30));
        int primeiroNumero = ThreadLocalRandom.current().nextInt(1, 10);
        int segundoNumero = ThreadLocalRandom.current().nextInt(10);
        int terceiroNumero = ThreadLocalRandom.current().nextInt(10);
        int quartoNumero = ThreadLocalRandom.current().nextInt(10);
        int numeroFormado = Integer.parseInt("" + primeiroNumero + segundoNumero + terceiroNumero + quartoNumero);
        token.setHash(Integer.toString(numeroFormado));
        return token;
    }

    private Token getToken(Usuario usuario){
        Token token = new Token();
        token.setUsuario(usuario);
        return token;
    }
}
