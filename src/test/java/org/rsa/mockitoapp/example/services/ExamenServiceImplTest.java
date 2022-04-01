package org.rsa.mockitoapp.example.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rsa.mockitoapp.example.daos.ExamenDao;
import org.rsa.mockitoapp.example.daos.PreguntaDao;
import org.rsa.mockitoapp.example.models.Examen;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
        List<Examen> datos = Arrays.asList(
                new Examen(5L, "Matemáticas"),
                new Examen(6L, "Lenguaje"),
                new Examen(7L, "Historia"));

        when(this.examenDao.finAll()).thenReturn(datos);

        Optional<Examen> examen = this.examenService.findExamenByNombre("Matemáticas");

        assertTrue(examen.isPresent());
        assertEquals(5L, examen.orElseThrow().getId());
        assertEquals("Matemáticas", examen.orElseThrow().getNombre());
    }

    @Test
    void finExamenByNombreListaVacia() {
        List<Examen> datos = Collections.emptyList();

        when(this.examenDao.finAll()).thenReturn(datos);

        Optional<Examen> examen = this.examenService.findExamenByNombre("Matemáticas");

        assertFalse(examen.isPresent());
    }

}