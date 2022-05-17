package org.rsa.mockitoapp.example.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rsa.mockitoapp.example.daos.ExamenDaoImpl;
import org.rsa.mockitoapp.example.daos.PreguntaDaoImpl;
import org.rsa.mockitoapp.example.models.Examen;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ExamenServiceImplSpyTest {

    @InjectMocks
    ExamenServiceImpl examenService;

    @Spy
    ExamenDaoImpl examenDao;

    @Spy
    PreguntaDaoImpl preguntaDao;

    @Test
    void testSpy() {
        List<String> preguntas = List.of("Aritmética");
        doReturn(preguntas).when(this.preguntaDao).findPreguntaByExamenId(anyLong());

        Examen examen = this.examenService.findExamenByNombreWithPreguntas("Matemáticas");

        assertEquals(5L, examen.getId());
        assertEquals("Matemáticas", examen.getNombre());
        assertEquals(1, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("Aritmética"));
        verify(this.examenDao).finAll();
        verify(this.preguntaDao).findPreguntaByExamenId(anyLong());
    }

}