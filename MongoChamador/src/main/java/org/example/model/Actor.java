package org.example.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "actores")
public class Actor {

    @Id
    private String idActor;

    private String nome;
    private String apelidos;
    private String nacionalidade;

    private Pelicula pelicula;

    public String getIdActor() {
        return idActor;
    }

    public void setIdActor(String idActor) {
        this.idActor = idActor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelidos() {
        return apelidos;
    }

    public void setApelidos(String apelidos) {
        this.apelidos = apelidos;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "idActor=" + idActor +
                ", nome='" + nome + '\'' +
                ", apelidos='" + apelidos + '\'' +
                ", nacionalidade='" + nacionalidade + '\'' +
                ", pelicula=" + pelicula +
                '}';
    }
}
