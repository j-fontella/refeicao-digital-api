package digital.refeicao.handlers;

import digital.refeicao.models.requisicao.Erro;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SegurancaValidationHandler {

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<Erro> handleException(SecurityException e) {
        Erro erro = new Erro();
        erro.getErros().add(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
    }

}
