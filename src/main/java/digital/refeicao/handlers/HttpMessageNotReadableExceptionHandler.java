package digital.refeicao.handlers;

import digital.refeicao.domains.Erros;
import digital.refeicao.models.requisicao.Erro;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HttpMessageNotReadableExceptionHandler extends RunTimeExceptionHandler{

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Erro> handleException(HttpMessageNotReadableException e) {
        return super.handleException(new RuntimeException(Erros.CONTATE_O_SUPORTE.getDescricao()));
    }

}
