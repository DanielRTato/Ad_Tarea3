package org.example.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

/**
 * @Entity: A diferencia de @Document en Mongo, @Entity indica que esta clase es una entidad JPA
 * que se mapeará a una tabla en una base de datos relacional (PostgreSQL).
 */
@Entity
@Table(name="peliculas")
public class Pelicula {

    /**
     * @Id: Clave primaria.
     * @GeneratedValue: En SQL solemos usar estrategias de autoincremento (IDENTITY).
     * En Mongo no es necesario, ya que él genera su propio ObjectId.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idpelicula")
    private Long id;

    @Column(name="titulo", length = 150)
    private String titulo;

    @Column(name = "xenero", length = 50)
    private String xenero;

    private int ano;

    /**
     * RELACIONES (Gran diferencia):
     * En SQL usamos @OneToMany, @ManyToOne, etc.
     * mappedBy: Define el lado inverso de la relación.
     * CascadeType.ALL: Si borras la película, se borran sus actores (integridad referencial).
     * FetchType.EAGER: Carga los actores inmediatamente al buscar la película.
     */
    @OneToMany(mappedBy = "pelicula", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Actor> actores;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public List<Actor> getActores() {
        return actores;
    }

    public void setActores(List<Actor> actores) {
        this.actores = actores;
    }
}
