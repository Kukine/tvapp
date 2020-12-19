package drumre.projekt.tvapp;

import drumre.projekt.tvapp.config.AppProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class TvappApplication {

	public static void main(String[] args) {
		SpringApplication.run(TvappApplication.class, args);
	}

}