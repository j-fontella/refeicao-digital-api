package digital.refeicao.utils;

import digital.refeicao.domains.Cargo;
import digital.refeicao.dtos.request.login.UsuarioRequestDTO;
import digital.refeicao.dtos.response.login.UsuarioCompletoResponseDTO;
import digital.refeicao.models.login.Conta;
import digital.refeicao.models.login.Usuario;
import digital.refeicao.models.requisicao.Erro;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

public class Utils {

    public static Usuario converterUsuarioRequest(UsuarioRequestDTO u){
        Usuario usuario = new ModelMapper().map(u, Usuario.class);
        Conta novaConta = new Conta();
        novaConta.setNome(u.getNomeConta());
        usuario.setConta(novaConta);
        usuario.setCargo(Cargo.PROPRIETARIO);
        usuario.setSenha(Utils.encriptarStringBCrypt(u.getSenha()));
        return usuario;
    }

    public static UsuarioCompletoResponseDTO converterUsuarioCompletoResponseDTO(Usuario u){
        return new ModelMapper().map(u, UsuarioCompletoResponseDTO.class);
    }

    public static String encriptarStringBCrypt(String str){
        return new BCryptPasswordEncoder().encode(str);
    }

    public static Erro gerarErro(String... msgErro){
        Erro erro = new Erro();
        Arrays.stream(msgErro).forEach(s -> erro.getErros().add(s));
        return erro;
    }

}
