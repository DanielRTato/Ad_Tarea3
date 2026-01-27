package org.example.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

/**
 * @Document: Anotación de Spring Data MongoDB para indicar que esta clase se mapea a una colección de Mongo.
 * Es el equivalente a @Entity en JPA/Hibernate.
 * collection = "peliculas": Define el nombre de la colección en la base de datos NoSQL.
 */
@Document(collection = "peliculas")
public class Pelicula {

    /**
     * @Id: Indica que este campo es la clave primaria del documento.
     * En MongoDB, si es String, Spring Data lo mapea automáticamente al campo "_id" (ObjectId).
     */
    @Id
    private String idPelicula;
    private String titulo;
    private String xenero;
    private Integer ano;

    /**
     * En MongoDB, las relaciones pueden ser embebidas (guardar los actores dentro del documento película)
     * o referenciadas (@DBRef). Aquí, por defecto, se guardarán como una lista embebida de objetos.
     */
    private List<Actor> actores;

    public String getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(String idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getXenero() {
        return xenero;
    }

    public void setXenero(String xenero) {
        this.xenero = xenero;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public List<Actor> getActores() {
        return actores;
    }

    public void setActores(List<Actor> actores) {
        this.actores = actores;
    }

    @Override
    public String toString() {
        return "Pelicula{" +
                "idPelicula=" + idPelicula +
                ", titulo='" + titulo + '\'' +
                ", xenero='" + xenero + '\'' +
                ", ano=" + ano +
                ", actores=" + actores +
                '}';
    }
}