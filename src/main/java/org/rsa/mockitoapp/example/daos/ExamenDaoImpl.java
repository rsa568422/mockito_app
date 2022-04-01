package org.rsa.mockitoapp.example.daos;

import org.rsa.mockitoapp.example.models.Examen;

import java.util.Arrays;
import java.util.List;

public class ExamenDaoImpl implements ExamenDao {

    @Override
    public List<Examen> finAll() {
        return Arrays.asList(
                new Examen(5L, "matemáticas"),
                new Examen(6L, "Lenguaje"),
                new Examen(7L, "Historia"));
    }

}
