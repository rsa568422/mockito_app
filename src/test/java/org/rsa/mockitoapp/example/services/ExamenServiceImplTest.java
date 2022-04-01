package org.rsa.mockitoapp.example.services;

import org.junit.jupiter.api.Test;
import org.rsa.mockitoapp.example.daos.ExamenDao;
import org.rsa.mockitoapp.example.models.Examen;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExamenServiceImplTest {

    @Test
    void finExamenByNombre() {
        ExamenDao examenDao = mock(ExamenDao.class);
        ExamenService examenService = new ExamenServiceImpl(examenDao);
        List<Examen> datos = Arrays.asList(
                new Examen(5L, "Matemáticas"),
                new Examen(6L, "Lenguaje"),
                new Examen(7L, "Historia"));

        when(examenDao.finAll()).thenReturn(datos);

        Optional<Examen> examen = examenService.findExamenByNombre("Matemáticas");

        assertTrue(examen.isPresent());
        assertEquals(5L, examen.orElseThrow().getId());
        assertEquals("Matemáticas", examen.orElseThrow().getNombre());
    }

    @Test
    void finExamenByNombreListaVacia() {
        ExamenDao examenDao = mock(ExamenDao.class);
        ExamenService examenService = new ExamenServiceImpl(examenDao);
        List<Examen> datos = Collections.emptyList();

        when(examenDao.finAll()).thenReturn(datos);

        Optional<Examen> examen = examenService.findExamenByNombre("Matemáticas");

        assertTrue(examen.isPresent());
        assertEquals(5L, examen.orElseThrow().getId());
        assertEquals("Matemáticas", examen.orElseThrow().getNombre());
    }

}