package digital.refeicao.handlers;

import digital.refeicao.models.requisicao.Erro;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MethodArgumentNotValidHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Erro> handleException(MethodArgumentNotValidException e) {
        Erro errors = new Erro();
        System.out.println(e.getMessage());
        e.getAllErrors().forEach(objectError -> errors.getErros().add(objectError.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}
