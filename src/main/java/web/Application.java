package web;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import repos.UserRepository;

/**
 * Main drive class which starts the spring boot website application.
 * 1. gradle build
 * 2. java -jar build/libs/gs-spring-boot-0.1.0.jar
 * 3. http://localhost:8080/
 */
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@EntityScan("data")
@SpringBootApplication
public class Application {

    // @Autowired
    // UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
