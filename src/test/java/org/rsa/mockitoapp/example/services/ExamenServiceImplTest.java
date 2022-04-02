package org.rsa.mockitoapp.example.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rsa.mockitoapp.example.daos.ExamenDao;
import org.rsa.mockitoapp.example.daos.PreguntaDao;
import org.rsa.mockitoapp.example.models.Examen;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExamenServiceImplTest {

    @InjectMocks
    ExamenServiceImpl examenService;

    @Mock
    ExamenDao examenDao;

    @Mock
    PreguntaDao preguntaDao;

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

    @Test
    void testPreguntasExamenVerify() {
        when(this.examenDao.finAll()).thenReturn(Datos.EXAMENES);
        when(this.preguntaDao.findPreguntaByExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);

        Examen examen = this.examenService.findExamenByNombreWithPreguntas("Matemáticas");

        assertEquals(5, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("Integrales"));
        verify(this.examenDao).finAll();
        verify(this.preguntaDao).findPreguntaByExamenId(anyLong());
    }

    @Test
    void testNoExisteExamenVerify() {
        when(this.examenDao.finAll()).thenReturn(Collections.emptyList());
        when(this.preguntaDao.findPreguntaByExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);

        Examen examen = this.examenService.findExamenByNombreWithPreguntas("Inexistente");

        assertNull(examen);
        verify(this.examenDao).finAll();
        verify(this.preguntaDao).findPreguntaByExamenId(5L);
    }

    @Test
    void testGuardarExamen() {
        Examen newExamen = Datos.EXAMEN;
        newExamen.setPreguntas(Datos.PREGUNTAS);

        when(this.examenDao.guardar(any(Examen.class))).thenReturn(Datos.EXAMEN);

        Examen examen = this.examenService.guardar(newExamen);

        assertNotNull(examen.getId());
        assertEquals(8L, examen.getId());
        assertEquals("Física", examen.getNombre());
        verify(this.examenDao).guardar(any(Examen.class));
        verify(this.preguntaDao).guardarVarias(anyList());
    }

}