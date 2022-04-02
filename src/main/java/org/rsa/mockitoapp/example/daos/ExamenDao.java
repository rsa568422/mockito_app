package org.rsa.mockitoapp.example.daos;

import org.rsa.mockitoapp.example.models.Examen;

import java.util.List;

public interface ExamenDao {

    List<Examen> finAll();

    Examen guardar(Examen examen);

}
