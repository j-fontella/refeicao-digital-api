package digital.refeicao.domains;

import lombok.Getter;

@Getter
public enum Cargo {
    OPERADOR("Operador"),
    ADMINISTRADOR("Administrador"),
    PROPRIETARIO("Proprietario");

    private final String descricao;

    Cargo(String descricao) {
        this.descricao = descricao;
    }
}
