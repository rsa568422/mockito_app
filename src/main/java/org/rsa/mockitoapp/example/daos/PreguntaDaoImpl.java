package org.rsa.mockitoapp.example.daos;

import org.rsa.mockitoapp.example.Datos;

import java.util.List;

public class PreguntaDaoImpl implements PreguntaDao {

    @Override
    public List<String> findPreguntaByExamenId(Long id) {
        System.out.println("PreguntaDaoImpl.findPreguntaByExamenId");
        return Datos.PREGUNTAS;
    }

    @Override
    public void guardarVarias(List<String> preguntas) {
        System.out.println("PreguntaDaoImpl.guardarVarias");
    }

}
