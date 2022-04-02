package org.rsa.mockitoapp.example.daos;

import java.util.List;

public interface PreguntaDao {

    List<String> findPreguntaByExamenId(Long id);

    void guardarVarias(List<String> preguntas);

}
