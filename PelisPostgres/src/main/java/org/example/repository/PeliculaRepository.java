package org.example.repository;

import org.example.model.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {

    List<Pelicula> findByTitulo(String titulo);
    //Optional<Pelicula> findByTitulo(String titulo);

}
