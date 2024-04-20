package digital.refeicao.utils;

import digital.refeicao.domains.Erros;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Utils {



    public static String criptografarBCrypt(String str){
        return new BCryptPasswordEncoder().encode(str);
    }

    public static void validarBCrypt(String str, String str2, Erros erro){
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        if(!bc.matches(str, str2)){
            throw new SecurityException(erro.toString());
        }
    }

    public static String base64Encode(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }
    public static String encodeBytesToBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }


    public static byte[] decodeToBytes(String base64) {
        return Base64.getDecoder().decode(base64);
    }

    public static String decodeFromBase64(String base64) {
        return new String(Base64.getDecoder().decode(base64));
    }

}
