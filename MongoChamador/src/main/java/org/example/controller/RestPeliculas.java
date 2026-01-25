package org.example.controller;

import org.example.model.Pelicula;
import org.example.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/peliculas")
public class RestPeliculas {

    @Autowired
    private PeliculaRepository peliculaRepository;

    @PostMapping
    public Pelicula create(@RequestBody Pelicula pelicula) {

        return peliculaRepository.save(pelicula);
    }

    @GetMapping("/{id}")
    public Pelicula obtenerPorId(@PathVariable Long id) {
        return peliculaRepository.findById(id).orElse(null);
    }

    @GetMapping("/buscar")
    public Pelicula getByTitulo(@RequestParam String titulo) {
        List<Pelicula> lista = peliculaRepository.findByTitulo(titulo);
        return lista.isEmpty() ? null : lista.get(0);
    }

}
