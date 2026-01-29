package org.example;

import org.example.model.Actor;
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

        System.out.println("--- 1. INSERTANDO DATOS EN POSTGRES ---");
        Pelicula peli1 = new Pelicula();
        peli1.setTitulo("Peli prueba");
        peli1.setAno(2024);
        peli1.setXenero("Comedia");
        Pelicula p1Guardada = conexion.createPelicula(peli1); // guardarla en Postgres

        if(p1Guardada != null) {
            System.out.println("--- 2. INSERTANDO ACTOR EN POSTGRES ---");
            Actor a1 = new Actor();
            a1.setNome("Actor Exemplo22");
            a1.setApelidos("apellido");
            a1.setIdPelicula(String.valueOf(p1Guardada.getIdPelicula()));
            conexion.createActor(a1);

            System.out.println("--- 3. INSERTANDO EN MONGO ---");
            p1Guardada.setActores(java.util.Arrays.asList(a1));
            peliculaService.crearPelicula(p1Guardada);
        }


        //Pelicula peliPorId = conexion.getAPeliculaById(1l);
//        List <Pelicula> peliPorTitulo = conexion.getPeliculaByTitulo("Pulp Fiction");
//
        // peliculaService.crearPelicula(peliPorId);
//        peliculaService.crearPelicula(peliPorTitulo.get(0));
//
//        List<Pelicula> lista_peliculas = conexion.getAllPeliculas();
//        for (Pelicula pelicula : lista_peliculas) {
//            System.out.println("Película recibida: " + pelicula);
//            peliculaService.crearPelicula(pelicula);
//        }

        peliculaService.exportarAJSON();

    }
}