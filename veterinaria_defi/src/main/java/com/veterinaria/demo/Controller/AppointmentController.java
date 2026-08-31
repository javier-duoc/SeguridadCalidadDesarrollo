package com.veterinaria.demo.Controller;

import com.veterinaria.demo.model.Appointment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class AppointmentController {

    // lista en memoria — simple para el curso
    private final List<Appointment> citas = new ArrayList<>();

    // POST /api/citas  →  privada (requiere JWT)
    @PostMapping
    public ResponseEntity<Appointment> crear(@RequestBody Appointment cita) {
        citas.add(cita);
        return ResponseEntity.ok(cita);
    }

    // GET /api/citas  →  privada (requiere JWT)
    @GetMapping
    public ResponseEntity<List<Appointment>> listar() {
        return ResponseEntity.ok(citas);
    }
}
