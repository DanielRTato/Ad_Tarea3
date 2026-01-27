package org.example.repository;

import org.example.model.Pelicula;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Repository: Marca la interfaz como un componente de acceso a datos.
 * MongoRepository<Pelicula, Long>: Hereda métodos CRUD (save, findAll, delete, etc.).
 * El primer parámetro es el tipo de objeto (Pelicula) y el segundo el tipo del ID.
 * NOTA: Si en el modelo el ID es String, aquí debería ser String.
 */
@Repository
public interface PeliculaRepository extends MongoRepository<Pelicula, Long> {

    /**
     * Query Method: Spring genera automáticamente la consulta basándose en el nombre del método.
     * En este caso, buscará en MongoDB documentos donde el campo "titulo" coincida.
     */
    List<Pelicula> findByTitulo(String titulo); 

}
