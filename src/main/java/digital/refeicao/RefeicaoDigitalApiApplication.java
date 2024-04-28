package digital.refeicao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(scanBasePackages = "digital.refeicao", exclude = {SecurityAutoConfiguration.class})
@EntityScan(basePackages = "digital.refeicao.models")
public class RefeicaoDigitalApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RefeicaoDigitalApiApplication.class, args);
	}

}
