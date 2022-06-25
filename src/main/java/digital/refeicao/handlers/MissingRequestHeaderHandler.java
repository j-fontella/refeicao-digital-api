package digital.refeicao.handlers;

import digital.refeicao.models.requisicao.Erro;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MissingRequestHeaderHandler {
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Erro> handleException(MissingRequestHeaderException e) {
        Erro errors = new Erro();
        errors.getErros().add("O parametro " + e.getHeaderName() + " deve ser passado no header da requisição.");
        return ResponseEntity.badRequest().body(errors);
    }
}
