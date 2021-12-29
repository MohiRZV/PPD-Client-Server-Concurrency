package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import service.Service;

@ComponentScan("controller")
@SpringBootApplication
public class StartServer {
    public static void main(String[] args) {
        Service.getInstance().nuke();
        SpringApplication.run(StartServer.class, args);
    }
}

