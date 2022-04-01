package org.rsa.mockitoapp.example.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rsa.mockitoapp.example.daos.ExamenDao;
import org.rsa.mockitoapp.example.daos.PreguntaDao;
import org.rsa.mockitoapp.example.models.Examen;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExamenServiceImplTest {

    ExamenDao examenDao;

    PreguntaDao preguntaDao;

    ExamenService examenService;

    @BeforeEach
    void setUp() {
        this.examenDao = mock(ExamenDao.class);
        this.preguntaDao = mock(PreguntaDao.class);
        this.examenService = new ExamenServiceImpl(this.examenDao, this.preguntaDao);
    }

    @Test
    void finExamenByNombre() {
        when(this.examenDao.finAll()).thenReturn(Datos.EXAMENES);

        Optional<Examen> examen = this.examenService.findExamenByNombre("Matemáticas");

        assertTrue(examen.isPresent());
        assertEquals(5L, examen.orElseThrow().getId());
        assertEquals("Matemáticas", examen.orElseThrow().getNombre());
    }

    @Test
    void finExamenByNombreListaVacia() {
        when(this.examenDao.finAll()).thenReturn(Collections.emptyList());

        Optional<Examen> examen = this.examenService.findExamenByNombre("Matemáticas");

        assertFalse(examen.isPresent());
    }

    @Test
    void testPreguntasExamen() {
        when(this.examenDao.finAll()).thenReturn(Datos.EXAMENES);
        when(this.preguntaDao.findPreguntaByExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);

        Examen examen = this.examenService.findExamenByNombreWithPreguntas("Matemáticas");

        assertEquals(5, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("Integrales"));
    }

}