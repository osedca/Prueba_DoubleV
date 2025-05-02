package com.doublev.prueba;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prueba")
public class PruebaDoubleVController {

    @GetMapping("/status")
    public String status() {
        return "Prueba DoubleV: Spring Boot está corriendo correctamente en Docker!";
    }

    @GetMapping("/info")
    public String info() {
        return "Este es el backend del proyecto Prueba DoubleV.";
    }
}
