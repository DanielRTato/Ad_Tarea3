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

/**
 * Servicio de conexión a otro microservicio (PelisPostgres).
 *
 * Propósito:
 * - Encapsular las llamadas HTTP hacia el servicio que expone datos en Postgres
 *   (endpoints /postgres/peliculas y /postgres/actores) para que otros componentes
 *   de la aplicación (por ejemplo `Secuencia` o `PeliculaService`) no necesiten lidiar
 *   con los detalles de HTTP.
 *
 * Comportamiento principal:
 * - Usa un RestTemplate inyectado para realizar peticiones HTTP GET/POST.
 * - Provee métodos de conveniencia: obtener todas las películas, obtener una por id,
 *   y crear una película en el servicio remoto.
 *
 * Notas:
 * - Esta clase actúa como un cliente HTTP (API client) dentro de la aplicación.
 * - Para producción conviene usar un logger en lugar de System.out y manejar errores
 *   con más detalle (reintentos, timeouts, circuit breaker).
 */
@Service
public class Conexion {
    // RestTemplate debe estar declarado como bean en la configuración de Spring
    // (por ejemplo, con @Bean en una clase @Configuration) para que Spring lo inyecte.
    @Autowired
    private RestTemplate restTemplate;

    // URLs hacia el servicio Postgres (PelisPostgres). En un proyecto real, estas URL
    // deberían venir de configuration (application.properties) y no estar hardcodeadas.
    private static final String POSTGRES_URL_PELICULAS = "http://localhost:8080/postgres/peliculas";
    private static final String POSTGRES_URL_ACTORES= "http://localhost:8080/postgres/actores";

    /**
     * Obtiene la lista completa de películas desde el servicio Postgres.
     *
     * Técnica:
     * - Usa restTemplate.exchange con ParameterizedTypeReference para deserializar
     *   correctamente un List<Pelicula> (necesario por la información de tipos en tiempo de ejecución).
     * - Si la respuesta es null o ocurre un error, devuelve una lista vacía en lugar de null.
     *
     * Retorno:
     * - List<Pelicula> con las películas o Collections.emptyList() si hay error/resultado vacío.
     */
    public List<Pelicula> getAllPeliculas() {
        try {
            String url = POSTGRES_URL_PELICULAS;
            // ParameterizedTypeReference permite preservar el tipo genérico List<Pelicula>
            ResponseEntity<List<Pelicula>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Pelicula>>() {}
            );
            // Evitar devolver null; preferir lista vacía para no forzar comprobaciones null en el llamador.
            return response.getBody() != null ? response.getBody() : Collections.emptyList();
        }  catch (Exception e) {
            // En vez de System.out, en producción usar un logger (logger.error(...)).
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

    /**
     * Obtiene una película por su id desde el servicio Postgres.
     *
     * Parámetros:
     * - id: identificador de la película a recuperar.
     *
     * Retorno:
     * - Pelicula si se encuentra; null si hay un error (p. ej. 404 o excepción cliente).
     *
     * Observaciones:
     * - Maneja HttpClientErrorException específicamente (errores 4xx),
     *   pero captura otras excepciones en getAllPeliculas; aquí se devuelve null cuando falla.
     */
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

    /**
     * Crea/POSTea una película al servicio Postgres.
     *
     * Parámetros:
     * - pelicula: objeto Pelicula que se enviará como JSON en el body.
     *
     * Retorno:
     * - La Pelicula devuelta por el servicio remoto (normalmente con id asignado) o null en caso de error.
     *
     * Observaciones:
     * - Se establecen cabeceras con Content-Type = application/json.
     * - En caso de fallo devuelve null y muestra un mensaje en consola.
     */
    public Pelicula createPelicula(Pelicula pelicula) {
        try {
            String url = POSTGRES_URL_PELICULAS;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Pelicula> request = new HttpEntity<>(pelicula, headers);

            // Usamos exchange con HttpMethod.POST para enviar el objeto y recibir la entidad creada.
            ResponseEntity<Pelicula> response = restTemplate.exchange(url, HttpMethod.POST, request, Pelicula.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            // Mensaje en consola; mejor usar logger.error y propagar o envolver la excepción según el caso.
            System.out.println("Error ao crear Pelicula: " + e.getMessage());
            return null;
        }
    }

    // Nota: la clase importa Actor pero no lo usa actualmente. Si se necesita obtener actores,
    // podríamos añadir métodos similares para getAllActores() y getUnActorById().
}
