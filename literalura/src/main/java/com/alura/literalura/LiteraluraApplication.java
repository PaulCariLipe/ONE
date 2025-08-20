package com.alura.literalura;

import com.alura.literalura.consola.Consola;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

    private final Consola consola;

    public LiterAluraApplication(Consola consola) {
        this.consola = consola;
    }

    public static void main(String[] args) {
        SpringApplication.run(LiterAluraApplication.class, args);
    }

    @Override
    public void run(String... args) {
        consola.iniciar();
    }
}
