package com.angelogomez.springwebfluxbase;

import com.angelogomez.springwebfluxbase.models.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringWebfluxBaseApplication implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(SpringWebfluxBaseApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(SpringWebfluxBaseApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // Subscribe a fondo
        Flux<Usuario> nombres = Flux.just("Angelo", "Gomez","d", "Pepito", "d", "Spring", "Webflux")
                .map(nombre -> new Usuario(nombre.toUpperCase(), null))
                .doOnNext(usuario -> {
                            if (usuario == null) {
                                throw new RuntimeException("Nombre vacio");
                            }
                            System.out.println(usuario.getNombre());

                })
                .map(usuario -> {
                    String nombre = usuario.toString();
                    usuario.setNombre(nombre.toLowerCase());
                    return usuario;
                });

        // Subscribe a nivel alto
        nombres.subscribe(usuario -> log.info(usuario.getNombre()),
                error -> log.error(error.getMessage()),
                new Runnable() {
                    @Override
                    public void run() {
                        log.info("Ha finalizado la secuencia");
                    }
                });
    }
}
