package ecc.mx.autenticacion.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class HelloController {
    @GetMapping("/hola")
    public String hola() {
        return "Hola Mundo!";
    }
    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }
}
