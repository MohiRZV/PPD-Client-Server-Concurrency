package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import service.Service;

@ComponentScan("controller")
@SpringBootApplication
public class Server {
    public static void main(String[] args) {
        new Service().nuke();
        SpringApplication.run(Server.class, args);
    }
}

