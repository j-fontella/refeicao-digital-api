package digital.refeicao.handlers;

import digital.refeicao.models.requisicao.Erro;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

public class RunTimeExceptionHandler {

    public ResponseEntity<Erro> handleException(RuntimeException e) {
        Erro erro = new Erro();
        erro.getErros().add(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
    }

}
