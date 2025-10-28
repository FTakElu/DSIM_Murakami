package teste;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class SistemaMonitoramentoPacientesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistemaMonitoramentoPacientesApplication.class, args);
    }
}