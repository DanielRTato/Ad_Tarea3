package org.example.service;

import com.google.gson.Gson;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.model.Pelicula;
import org.example.repository.ActorRepository;
import org.example.repository.PeliculaRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

@Service
public class PeliculaService {

    private final PeliculaRepository peliculaRepository;
    private final ActorRepository actorRepository;

    public PeliculaService(PeliculaRepository peliculaRepository, ActorRepository actorRepository) {
        this.peliculaRepository = peliculaRepository;
        this.actorRepository = actorRepository;
    }

    public void crearPelicula(Pelicula pelicula){
        peliculaRepository.save(pelicula);
    }

    public List<Pelicula> findAll(){
        return peliculaRepository.findAll();
    }

    public List<Pelicula> importarDesdeJSON(String ruta) throws Exception {
        try(Reader reader = new InputStreamReader(new ClassPathResource(ruta).getInputStream())) {

            Type listType = new com.google.gson.reflect.TypeToken<List<Pelicula>>() {
            }.getType();

            List<Pelicula> lista_peliculas = new Gson().fromJson(reader, listType);

            return peliculaRepository.saveAll(lista_peliculas);
        }
    }

    public void exportarAJSON() {
        List<Pelicula> lista_peliculas = findAll();

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(new File("peliculas.json"), lista_peliculas);
            System.out.println("Peliculas exportadas a peliculas.json");

        } catch (IOException e) {
            System.out.println("Error al exportar las peliculas a JSON: " + e.getMessage());
        }
    }
}
