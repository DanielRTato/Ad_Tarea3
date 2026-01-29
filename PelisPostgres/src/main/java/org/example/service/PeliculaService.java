package org.example.service;

import org.example.model.Actor;
import org.example.model.Pelicula;
import org.example.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PeliculaService {

    private final PeliculaRepository peliculaRepository;

    @Autowired
    public PeliculaService(PeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
    }

    public Pelicula save(Pelicula pelicula) {
        // Si la película trae actores, hay que decirles a cada uno quién es su padre
        if (pelicula.getActores() != null) {
            for (Actor actor : pelicula.getActores()) {
                actor.setPelicula(pelicula);
            }
        }
        return peliculaRepository.save(pelicula);
       // return peliculaRepository.save(pelicula);
    }

    public boolean exists(Long id) {
        return peliculaRepository.existsById(id);
    }

    public void delete(Long id) {
        peliculaRepository.deleteById(id);
    }

    public List<Pelicula> findByTitulo(String titulo) {
        return peliculaRepository.findByTitulo(titulo);
    }

    public List<Pelicula> findAll() {
        return peliculaRepository.findAll();
    }

    public Optional<Pelicula> findById(Long id) {

        return peliculaRepository.findById(id);
    }

    public List<Pelicula> obtenerPeliculaPorTitulo(String titulo) {
        return peliculaRepository.findByTitulo(titulo);
    }

}
