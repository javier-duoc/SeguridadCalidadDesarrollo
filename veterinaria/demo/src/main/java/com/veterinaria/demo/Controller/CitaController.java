package com.veterinaria.demo.Controller;

import com.veterinaria.demo.model.Cita;
import com.veterinaria.demo.model.Paciente;
import com.veterinaria.demo.repository.CitaRepository;
import com.veterinaria.demo.repository.PacienteRepository;
import com.veterinaria.demo.repository.VeterinarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Rutas PRIVADAS de citas. Requieren sesion iniciada.
 */
@Controller
@RequestMapping("/citas")
public class CitaController {

    private final CitaRepository citaRepository;
    private final PacienteRepository pacienteRepository;
    private final VeterinarioRepository veterinarioRepository;

    public CitaController(CitaRepository citaRepository,
                          PacienteRepository pacienteRepository,
                          VeterinarioRepository veterinarioRepository) {
        this.citaRepository = citaRepository;
        this.pacienteRepository = pacienteRepository;
        this.veterinarioRepository = veterinarioRepository;
    }

    /** GET /citas -> agenda de citas (cualquier usuario autenticado) */
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("citas", citaRepository.listarTodas());
        model.addAttribute("nombresPacientes", nombresPacientesPorId());
        return "citas/lista";
    }

    /** GET /citas/nueva -> formulario para programar una cita */
    @GetMapping("/nueva")
    public String formularioNueva(Model model) {
        model.addAttribute("cita", new Cita());
        cargarCombos(model);
        return "citas/formulario";
    }

    /** POST /citas -> guarda la cita en memoria */
    @PostMapping
    public String guardar(@ModelAttribute Cita cita,
                          RedirectAttributes redirectAttributes,
                          Model model) {

        if (cita.getPacienteId() == null || cita.getFecha() == null || cita.getHora() == null
                || cita.getMotivo() == null || cita.getMotivo().isBlank()
                || cita.getVeterinario() == null || cita.getVeterinario().isBlank()) {
            model.addAttribute("error", "Todos los campos de la cita son obligatorios.");
            model.addAttribute("cita", cita);
            cargarCombos(model);
            return "citas/formulario";
        }

        citaRepository.guardar(cita);
        redirectAttributes.addFlashAttribute("mensaje", "Cita programada correctamente.");
        return "redirect:/citas";
    }

    /** POST /citas/{id}/eliminar -> cancela una cita (solo ADMIN) */
    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        citaRepository.eliminarPorId(id);
        redirectAttributes.addFlashAttribute("mensaje", "Cita cancelada.");
        return "redirect:/citas";
    }

    private void cargarCombos(Model model) {
        model.addAttribute("pacientes", pacienteRepository.listarTodos());
        model.addAttribute("veterinarios", veterinarioRepository.listarTodos());
    }

    /** Mapa id -> nombre, para mostrar el nombre del paciente en la tabla de citas. */
    private Map<Long, String> nombresPacientesPorId() {
        Map<Long, String> nombres = new LinkedHashMap<>();
        for (Paciente paciente : pacienteRepository.listarTodos()) {
            nombres.put(paciente.getId(), paciente.getNombre());
        }
        return nombres;
    }
}
