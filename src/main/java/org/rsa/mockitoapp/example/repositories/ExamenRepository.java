package org.rsa.mockitoapp.example.repositories;

import org.rsa.mockitoapp.example.models.Examen;

import java.util.List;

public interface ExamenRepository {
    Examen guardar(Examen examen);
    List<Examen> findAll();
}
