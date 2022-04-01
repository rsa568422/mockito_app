package org.rsa.mockitoapp.example.daos;

import org.rsa.mockitoapp.example.models.Examen;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExamenDaoAlt implements ExamenDao {

    @Override
    public List<Examen> finAll() {
        try {
            System.out.println("ExamenDaoAlt");
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
