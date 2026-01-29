package org.example.service;

import org.example.model.Actor;
import org.example.model.Pelicula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class Conexion {

    @Autowired
    private RestTemplate restTemplate;

    private static final String POSTGRES_URL_PELICULAS = "http://localhost:8081/postgres/peliculas";
    private static final String POSTGRES_URL_ACTORES= "http://localhost:8081/postgres/actores";

    public List<Pelicula> getAllPeliculas() {
        try {
            String url = POSTGRES_URL_PELICULAS;
            ResponseEntity<List<Pelicula>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Pelicula>>() {}
            );
            return response.getBody() != null ? response.getBody() : Collections.emptyList();
        }  catch (Exception e) {
            System.out.println("Error al obtener las peliculas: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Pelicula> getPeliculaByTitulo(String titulo) {
        try {
            String url = POSTGRES_URL_PELICULAS + "/titulo/" + titulo;
            ResponseEntity<List<Pelicula>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Pelicula>>() {});
            return response.getBody();
        } catch (HttpClientErrorException e) {
            System.out.println("Error al obtener la pelicula con titulo " + titulo + ": " + e.getMessage());
            return null;
        }
    }

    public Pelicula getAPeliculaById(Long id) {
        try {
            String url = POSTGRES_URL_PELICULAS + "/" + id;
            ResponseEntity<Pelicula> response = restTemplate.exchange(url, HttpMethod.GET, null, Pelicula.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            System.out.println("Error al obtener la pelicula " + id + ": " + e.getMessage());
            return null;
        }
    }


    public Pelicula createPelicula(Pelicula pelicula) {
        try {
            String url = POSTGRES_URL_PELICULAS;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Pelicula> request = new HttpEntity<>(pelicula, headers);

            ResponseEntity<Pelicula> response = restTemplate.exchange(url, HttpMethod.POST, request, Pelicula.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            System.out.println("Error ao crear Pelicula: " + e.getMessage());
            return null;
        }
    }

    public Actor createActor(Actor actor) {
        try {
            String url = POSTGRES_URL_ACTORES;
            // Configurar headers y request igual que en peliculas
            return restTemplate.postForObject(url, actor, Actor.class);
        } catch (Exception e) {
            System.out.println("Error creando actor: " + e.getMessage());
            return null;
        }
    }

    // Nota: la clase importa Actor pero no lo usa actualmente. Si se necesita obtener actores,
    // podríamos añadir métodos similares para getAllActores() y getUnActorById().
}
