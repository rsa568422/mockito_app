package org.rsa.mockitoapp.example.services;

import org.rsa.mockitoapp.example.daos.ExamenDao;
import org.rsa.mockitoapp.example.models.Examen;

import java.util.Optional;

public class ExamenServiceImpl implements ExamenService {

    private ExamenDao examenDao;

    public ExamenServiceImpl(ExamenDao examenDao) {
        this.examenDao = examenDao;
    }

    @Override
    public Optional<Examen> findExamenByNombre(String nombre) {
        return this.examenDao
                .finAll()
                .stream()
                .filter(examen -> examen.getNombre().contains(nombre))
                .findAny();
    }

}
