package com.veterinaria.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.veterinaria.demo.model.Veterinario;

/**
 * "Base de datos" en memoria del equipo veterinario.
 * Se usa para llenar el combo "Veterinario asignado" del formulario de citas.
 */
@Repository
public class VeterinarioRepository {

    private final List<Veterinario> veterinarios = new ArrayList<>();

    public VeterinarioRepository() {
        veterinarios.add(new Veterinario(1L, "Dra. Carla Munoz", "Medicina general"));
        veterinarios.add(new Veterinario(2L, "Dr. Luis Herrera", "Cirugia"));
        veterinarios.add(new Veterinario(3L, "Dra. Paula Vidal", "Dermatologia"));
    }

    public List<Veterinario> listarTodos() {
        return new ArrayList<>(veterinarios);
    }
}

    

