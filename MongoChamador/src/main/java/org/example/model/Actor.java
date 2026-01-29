package org.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "actores")
public class Actor {
    @Id
    private String idactor;

    private String nome;
    private String apelidos;
    private String nacionalidade;

    private String idpelicula;

    public String getIdActor() {
        return idactor;
    }

    public void setIdActor(String idActor) {
        this.idactor = idActor;
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

    public String getIdPelicula() {
        return idpelicula;
    }

    public void setIdPelicula(String idPelicula) {
        this.idpelicula = idPelicula;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "idActor=" + idactor +
                ", nome='" + nome + '\'' +
                ", apelidos='" + apelidos + '\'' +
                ", nacionalidade='" + nacionalidade + '\'' +
                ", pelicula=" + idpelicula +
                '}';
    }
}
