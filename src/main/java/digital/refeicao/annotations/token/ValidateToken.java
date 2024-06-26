package digital.refeicao.annotations.token;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ValidateToken {
    // Adicione parâmetros se necessário, como 'prk' para identificar o usuário
}
