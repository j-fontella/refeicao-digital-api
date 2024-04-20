package digital.refeicao.domains;

import lombok.Getter;

@Getter
public enum Erros {
    SENHA_INCORRETA("Senha incorreta"),
    USUARIO_NAO_ENCONTRADO("Este usuário não está cadastrado no sistema."),

    CONTATE_O_SUPORTE("Erro ao realizar requisição, contate o suporte"),
    //Ausencia de Token no header da requisição
    CONTATE_O_SUPORTE_1("Erro número 1, contate o suporte com este código"),
    USUARIO_JA_CADASTRADO("Este usuário já está cadastrado no sistema"),
    ACESSO_EXPIRADO("Acesso expirado, realize o login novamente");



    private final String descricao;

    Erros(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
