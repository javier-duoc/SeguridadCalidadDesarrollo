package com.veterinaria.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.veterinaria.demo.model.Paciente;


    /**
 * "Base de datos" en memoria de pacientes.
 * Mismo esquema que los usuarios en memoria de Spring Security: los datos viven
 * mientras la aplicacion este ejecutandose y se pierden al reiniciarla.
 */
@Repository
public class PacienteRepository {

    private final List<Paciente> pacientes = new ArrayList<>();
    private final AtomicLong secuencia = new AtomicLong(0);

    public PacienteRepository() {
        // Datos de ejemplo para que la aplicacion no parta vacia
        guardar(new Paciente(null, "Firulais", "Perro", "Quiltro", 4, "Maria Gonzalez"));
        guardar(new Paciente(null, "Michi", "Gato", "Siames", 2, "Pedro Soto"));
        guardar(new Paciente(null, "Rocky", "Perro", "Labrador", 7, "Ana Rojas"));
    }

    public List<Paciente> listarTodos() {
        return new ArrayList<>(pacientes);
    }

    public Optional<Paciente> buscarPorId(Long id) {
        return pacientes.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public Paciente guardar(Paciente paciente) {
        if (paciente.getId() == null) {
            paciente.setId(secuencia.incrementAndGet());
            pacientes.add(paciente);
        } else {
            buscarPorId(paciente.getId()).ifPresent(existente -> {
                existente.setNombre(paciente.getNombre());
                existente.setEspecie(paciente.getEspecie());
                existente.setRaza(paciente.getRaza());
                existente.setEdad(paciente.getEdad());
                existente.setDueno(paciente.getDueno());
            });
        }
        return paciente;
    }

    public void eliminarPorId(Long id) {
        pacientes.removeIf(p -> p.getId().equals(id));
    }
}

    

