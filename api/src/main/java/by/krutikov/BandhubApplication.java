package by.krutikov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "by.krutikov")
@EnableTransactionManagement
public class BandhubApplication {

    public static void main(String[] args) {
        SpringApplication.run(BandhubApplication.class, args);
    }

}
