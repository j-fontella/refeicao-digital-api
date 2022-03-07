package digital.refeicao.domains;

import lombok.Getter;

@Getter
public enum Erros {
    SENHA_INCORRETA("Senha incorreta"),
    USUARIO_NAO_ENCONTRADO("Este usuário não está cadastrado no sistema."),
    USUARIO_JA_CADASTRADO("Este usuário já está cadastrado no sistema");

    private final String descricao;

    Erros(String descricao) {
        this.descricao = descricao;
    }
}
