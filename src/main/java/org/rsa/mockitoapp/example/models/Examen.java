package org.rsa.mockitoapp.example.models;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class Examen {

    private Long id;

    private String nombre;

    private List<String> preguntas;

    public Examen(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.preguntas = new LinkedList<>();
    }
}
