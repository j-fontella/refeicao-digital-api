package digital.refeicao.annotations.token;

import digital.refeicao.exceptions.NegocioException;
import digital.refeicao.services.login.TokenService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static digital.refeicao.domains.Erros.CONTATE_O_SUPORTE;
import static digital.refeicao.domains.Erros.CONTATE_O_SUPORTE_1;
import static digital.refeicao.utils.Utils.decodeFromBase64;
import static java.lang.Long.parseLong;

@Aspect
@Component
public class TokenValidationAspect {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @Before("@annotation(digital.refeicao.annotations.token.ValidateToken)")
    public void validateToken(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String token = httpServletRequest.getHeader("token"); // Obter token do cabeçalho
        if (token == null) {
            throw new NegocioException(CONTATE_O_SUPORTE_1);
        }
        try{
            token = decodeFromBase64(token);
            String[] tokenInfo = token.split(":");
            tokenService.validarToken(tokenInfo[1], parseLong(tokenInfo[0]));
        }catch (Exception e){
            if(e instanceof NegocioException){
                throw e;
            }else{
                throw new NegocioException(CONTATE_O_SUPORTE);
            }
        }
    }
}
