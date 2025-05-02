package com.doublev.prueba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.doublev.prueba") // Forzar el escaneo de controladores
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
