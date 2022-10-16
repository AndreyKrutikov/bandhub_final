package by.krutikov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "by.krutikov")
public class BandhubApplication {

    public static void main(String[] args) {
        SpringApplication.run(BandhubApplication.class, args);
    }

}
