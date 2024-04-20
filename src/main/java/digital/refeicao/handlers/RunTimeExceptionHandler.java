package digital.refeicao.handlers;

import digital.refeicao.models.requisicao.Erro;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class RunTimeExceptionHandler {

    public ResponseEntity<Erro> handleException(RuntimeException e) {
        Erro erro = new Erro();
        erro.getErros().add(e.getMessage());
        return ResponseEntity.status(BAD_REQUEST).body(erro);
    }

}
