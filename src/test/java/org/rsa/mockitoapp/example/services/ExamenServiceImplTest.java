package org.rsa.mockitoapp.example.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
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

    @Captor
    ArgumentCaptor<Long> captor;

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
        // Given
        when(this.examenDao.finAll()).thenReturn(Collections.emptyList());
        when(this.preguntaDao.findPreguntaByExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);

        // When
        Examen examen = this.examenService.findExamenByNombreWithPreguntas("Inexistente");

        // Then
        assertNull(examen);
        verify(this.examenDao).finAll();
        verify(this.preguntaDao).findPreguntaByExamenId(5L);
    }

    @Test
    void testGuardarExamen() {
        // Given
        Examen newExamen = Datos.EXAMEN;
        newExamen.setPreguntas(Datos.PREGUNTAS);

        when(this.examenDao.guardar(any(Examen.class))).then(new Answer<Examen>() {
            Long secuencia = 8L;
            @Override
            public Examen answer(InvocationOnMock invocation) throws Throwable {
                Examen examen = invocation.getArgument(0);
                examen.setId(secuencia++);
                return examen;
            }
        });

        // When
        Examen examen = this.examenService.guardar(newExamen);

        // Then
        assertNotNull(examen.getId());
        assertEquals(8L, examen.getId());
        assertEquals("Física", examen.getNombre());
        verify(this.examenDao).guardar(any(Examen.class));
        verify(this.preguntaDao).guardarVarias(anyList());
    }

    @Test
    void testManejoException() {
        when(this.examenDao.finAll()).thenReturn(Datos.EXAMENES_ID_NULL);
        when(this.preguntaDao.findPreguntaByExamenId(isNull())).thenThrow(IllegalArgumentException.class);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> this.examenService.findExamenByNombreWithPreguntas("Matemáticas"));
        assertEquals(IllegalArgumentException.class, exception.getClass());
        verify(this.examenDao).finAll();
        verify(this.preguntaDao).findPreguntaByExamenId(isNull());
    }

    @Test
    void testArgumentMatchers() {
        when(this.examenDao.finAll()).thenReturn(Datos.EXAMENES);
        when(this.preguntaDao.findPreguntaByExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);

        this.examenService.findExamenByNombreWithPreguntas("Matemáticas");

        verify(this.examenDao).finAll();
        verify(this.preguntaDao).findPreguntaByExamenId(eq(5L));
        verify(this.preguntaDao).findPreguntaByExamenId(argThat(arg -> arg != null && arg.compareTo(5L) >= 0));
    }

    @Test
    void testArgumentMatchers2() {
        when(this.examenDao.finAll()).thenReturn(Datos.EXAMENES);
        when(this.preguntaDao.findPreguntaByExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);

        this.examenService.findExamenByNombreWithPreguntas("Matemáticas");

        verify(this.examenDao).finAll();
        verify(this.preguntaDao).findPreguntaByExamenId(argThat(new MiArgsMatchers()));
    }

    @Test
    void testArgumentMatchers3() {
        when(this.examenDao.finAll()).thenReturn(Datos.EXAMENES);
        when(this.preguntaDao.findPreguntaByExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);

        this.examenService.findExamenByNombreWithPreguntas("Matemáticas");

        verify(this.examenDao).finAll();
        verify(this.preguntaDao).findPreguntaByExamenId(argThat(argument -> argument != null && argument > 0));
    }

    public static class MiArgsMatchers implements ArgumentMatcher<Long> {

        private Long argument;

        @Override
        public boolean matches(Long argument) {
            this.argument = argument;
            return this.argument != null && this.argument > 0;
        }

        @Override
        public String toString() {
            return "es para un mensaje personalizado de error que imprime mockito en caso de que falle el test, "
                    .concat(String.format("debe ser un número entero positivo: %d", this.argument));
        }
    }

    @Test
    void testArgumentCaptor() {
        when(this.examenDao.finAll()).thenReturn(Datos.EXAMENES);
        when(this.preguntaDao.findPreguntaByExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);

        this.examenService.findExamenByNombreWithPreguntas("Matemáticas");

        //ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        verify(this.preguntaDao).findPreguntaByExamenId(this.captor.capture());

        assertEquals(5L, captor.getValue());
    }

}