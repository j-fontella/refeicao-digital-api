package digital.refeicao.domains;

import lombok.Getter;

@Getter
public enum Erros {
    SENHA_INCORRETA("Senha incorreta"),
    USUARIO_JA_CADASTRADO("Este usuário já está cadastrado no sistema"),

    USUARIO_NAO_ENCONTRADO("Este usuário não está cadastrado no sistema."),

    UNIDADE_NAO_ENCONTRADA("Esta unidade não está cadastrada no sistema."),
    CONTATE_O_SUPORTE("Erro ao realizar requisição, contate o suporte"),
    //Ausencia de Token no header da requisição
    CONTATE_O_SUPORTE_1("Erro número 1, contate o suporte com este código"),
    ACESSO_EXPIRADO("Acesso expirado, realize o login novamente"),
    PRODUTO_JA_CADASTRADO("Este produto já está cadastrado no sistema"),

    PRODUTO_NAO_ENCONTRADO("Este produto não está cadastrado no sistema."),
    CATEGORIA_JA_CADASTRADA("Esta categoria já está cadastrada no sistema"),

    CATEGORIA_NAO_ENCONTRADA("Esta categoria não está cadastrada no sistema.");




    private final String descricao;

    Erros(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
