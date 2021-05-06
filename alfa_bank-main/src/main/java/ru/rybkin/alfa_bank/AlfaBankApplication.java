package ru.rybkin.alfa_bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AlfaBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlfaBankApplication.class, args);
    }

}
