package org.rsa.mockitoapp.example.daos;

import org.rsa.mockitoapp.example.Datos;
import org.rsa.mockitoapp.example.models.Examen;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExamenDaoImpl implements ExamenDao {

    @Override
    public List<Examen> finAll() {
        try {
            System.out.println("ExamenDaoAlt");
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ExamenDaoImpl.finAll");
        return Datos.EXAMENES;
    }

    @Override
    public Examen guardar(Examen examen) {
        System.out.println("ExamenDaoImpl.guardar");
        return Datos.EXAMEN;
    }
}
