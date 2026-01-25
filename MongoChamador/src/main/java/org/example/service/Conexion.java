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

    private static final String POSTGRES_URL_PELICULAS = "http://localhost:8080/postgres/peliculas";
    private static final String POSTGRES_URL_ACTORES= "http://localhost:8080/postgres/actores";

    public List<Pelicula> getAllPeliculas() {
        try {
            String url = POSTGRES_URL_PELICULAS;
            ResponseEntity<List<Pelicula>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Pelicula>>() {});

            return response.getBody() != null ? response.getBody() : Collections.emptyList();
        }  catch (Exception e) {
            System.out.println("Error al obtener las peliculas: " + e.getMessage());
            return Collections.emptyList();
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
    }



