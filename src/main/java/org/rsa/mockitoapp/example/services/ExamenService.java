package org.rsa.mockitoapp.example.services;

import org.rsa.mockitoapp.example.models.Examen;

import java.util.Optional;

public interface ExamenService {

    Optional<Examen> findExamenByNombre(String nombre);

    Examen findExamenByNombreWithPreguntas(String nombre);

    Examen guardar(Examen examen);

}
