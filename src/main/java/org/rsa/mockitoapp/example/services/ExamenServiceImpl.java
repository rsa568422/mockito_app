package org.rsa.mockitoapp.example.services;

import org.rsa.mockitoapp.example.daos.ExamenDao;
import org.rsa.mockitoapp.example.models.Examen;

public class ExamenServiceImpl implements ExamenService {

    private ExamenDao examenDao;

    public ExamenServiceImpl(ExamenDao examenDao) {
        this.examenDao = examenDao;
    }

    @Override
    public Examen finExamenByNombre(String nombre) {
        return this.examenDao
                .finAll()
                .stream()
                .filter(examen -> examen.getNombre().contains(nombre))
                .findAny()
                .orElseThrow();
    }

}
