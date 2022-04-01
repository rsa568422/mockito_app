package org.rsa.mockitoapp.example.services;

import org.rsa.mockitoapp.example.daos.ExamenDao;
import org.rsa.mockitoapp.example.daos.PreguntaDao;
import org.rsa.mockitoapp.example.models.Examen;

import java.util.List;
import java.util.Optional;

public class ExamenServiceImpl implements ExamenService {

    private ExamenDao examenDao;

    private PreguntaDao preguntaDao;

    public ExamenServiceImpl(ExamenDao examenDao, PreguntaDao preguntaDao) {
        this.examenDao = examenDao;
        this.preguntaDao = preguntaDao;
    }

    @Override
    public Optional<Examen> findExamenByNombre(String nombre) {
        return this.examenDao
                .finAll()
                .stream()
                .filter(examen -> examen.getNombre().contains(nombre))
                .findAny();
    }

    @Override
    public Examen findExamenByNombreWithPreguntas(String nombre) {
        Optional<Examen> examenOptional = this.findExamenByNombre(nombre);
        Examen examen = null;
        if (examenOptional.isPresent()) {
            examen = examenOptional.orElseThrow();
            List<String> preguntas = this.preguntaDao.findPreguntaByExamenId(examen.getId());
            examen.setPreguntas(preguntas);
        }
        return examen;
    }

}
