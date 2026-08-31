package com.veterinaria.demo.repository;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.veterinaria.demo.model.Cita;

/**
 * "Base de datos" en memoria de citas.
 */
@Repository
public class CitaRepository {

    private final List<Cita> citas = new ArrayList<>();
    private final AtomicLong secuencia = new AtomicLong(0);

    public CitaRepository() {
        guardar(new Cita(null, 1L, LocalDate.now().plusDays(1), LocalTime.of(10, 30),
                "Vacuna anual", "Dra. Carla Munoz"));
        guardar(new Cita(null, 2L, LocalDate.now().plusDays(2), LocalTime.of(16, 0),
                "Control de peso", "Dr. Luis Herrera"));
    }

    public List<Cita> listarTodas() {
        List<Cita> copia = new ArrayList<>(citas);
        copia.sort(Comparator.comparing(Cita::getFecha).thenComparing(Cita::getHora));
        return copia;
    }

    public Optional<Cita> buscarPorId(Long id) {
        return citas.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    public Cita guardar(Cita cita) {
        if (cita.getId() == null) {
            cita.setId(secuencia.incrementAndGet());
            citas.add(cita);
        }
        return cita;
    }

    public void eliminarPorId(Long id) {
        citas.removeIf(c -> c.getId().equals(id));
    }
}

    

