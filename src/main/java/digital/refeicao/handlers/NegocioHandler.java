package digital.refeicao.handlers;

import digital.refeicao.exceptions.NegocioException;
import digital.refeicao.models.requisicao.Erro;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NegocioHandler extends RunTimeExceptionHandler {

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Erro> handleException(NegocioException e) {
        return super.handleException(e);
    }

}
