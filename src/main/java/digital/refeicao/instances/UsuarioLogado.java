package digital.refeicao.instances;

import digital.refeicao.models.login.Usuario;

public class UsuarioLogado {

    private static Usuario logado;

    public static Usuario getInstance(){
        if(logado == null){
            logado = new Usuario();
            return logado;
        }
        return logado;
    }
}
