package org.example;

import org.example.model.Pelicula;
import org.example.service.Conexion;
import org.example.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Secuencia {

    private final Conexion conexion;
    private final PeliculaService peliculaService;

    @Autowired
    public Secuencia(Conexion conexion, PeliculaService peliculaService) {
        this.conexion = conexion;
        this.peliculaService = peliculaService;

    }

    public void executar() {

        List<Pelicula> lista_peliculas = conexion.getAllPeliculas();
        for (Pelicula pelicula : lista_peliculas) {
            System.out.println("Película recibida: " + pelicula);
            peliculaService.crearPelicula(pelicula);

        }

        peliculaService.exportarAJSON();

    }
}