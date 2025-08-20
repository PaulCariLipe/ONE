package com.alura.literalura.consola;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.GutendexService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Consola {

    private final GutendexService gutendexService;
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final Scanner scanner = new Scanner(System.in);

    public Consola(GutendexService gutendexService,
                   LibroRepository libroRepository,
                   AutorRepository autorRepository) {
        this.gutendexService = gutendexService;
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void iniciar() {
        while (true) {
            System.out.println("\n===== Menú LiterAlura =====");
            System.out.println("1 - Buscar libro por título");
            System.out.println("2 - Listar libros registrados");
            System.out.println("3 - Listar autores registrados");
            System.out.println("4 - Listar autores vivos en determinado año");
            System.out.println("5 - Listar libros por idioma");
            System.out.println("0 - Salir");
            System.out.print("Opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> buscarLibro();
                case 2 -> listarLibros();
                case 3 -> listarAutores();
                case 4 -> listarAutoresVivos();
                case 5 -> listarLibrosPorIdioma();
                case 0 -> {
                    System.out.println("Adiós!");
                    return;
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private void buscarLibro() {
        System.out.print("Ingrese título: ");
        String titulo = scanner.nextLine();

        if (libroRepository.findByTitulo(titulo).isPresent()) {
            System.out.println("⚠️ El libro ya está en la base de datos.");
            return;
        }

        Libro libro = gutendexService.buscarLibro(titulo);
        if (libro == null) {
            System.out.println("❌ Libro no encontrado.");
            return;
        }

        autorRepository.save(libro.getAutor());
        libroRepository.save(libro);

        System.out.println("✅ Libro registrado: " + libro.getTitulo() +
                " - Autor: " + libro.getAutor().getNombre());
    }

    private void listarLibros() {
        libroRepository.findAll().forEach(
                l -> System.out.println(l.getTitulo() + " - " + l.getAutor().getNombre())
        );
    }

    private void listarAutores() {
        autorRepository.findAll().forEach(
                a -> System.out.println(a.getNombre() +
                        " (" + a.getNacimiento() + " - " + a.getFallecimiento() + ")")
        );
    }

    private void listarAutoresVivos() {
        System.out.print("Ingrese año: ");
        int anio = scanner.nextInt();

        autorRepository.findAll().stream()
                .filter(a -> a.getNacimiento() != null && a.getNacimiento() <= anio
                        && (a.getFallecimiento() == null || a.getFallecimiento() >= anio))
                .forEach(a -> System.out.println(a.getNombre()));
    }

    private void listarLibrosPorIdioma() {
        System.out.print("Ingrese idioma (ES, EN, FR, PT): ");
        String idioma = scanner.nextLine();

        libroRepository.findByIdioma(idioma)
                .forEach(l -> System.out.println(l.getTitulo()));
    }
}
