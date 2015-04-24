import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Date: 23/03/15.
 * Time: 16:53
 */
@Configuration
@EnableAutoConfiguration
public class Main {
	
	public static void main(String[] args) throws Exception {
		//SpringApplication.run(Main.class, args);
		SpringApplication app = new SpringApplication(Antihacker.class);
		app.setShowBanner(false);
		app.run(args);
	}
}
