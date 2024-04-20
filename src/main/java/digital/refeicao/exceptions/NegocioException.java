package digital.refeicao.exceptions;


import digital.refeicao.domains.Erros;

public class NegocioException extends RuntimeException {

    public NegocioException(Erros erro) {
        super(erro.toString());
    }
}
