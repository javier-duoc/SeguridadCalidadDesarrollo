package com.veterinaria.demo.Controller;

import com.veterinaria.demo.model.Paciente;
import com.veterinaria.demo.repository.PacienteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Rutas PRIVADAS de pacientes. Requieren sesion iniciada.
 * Las reglas de que rol puede entrar a cada una estan en WebSecurityConfig.
 */
@Controller
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteRepository pacienteRepository;

    public PacienteController(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    /** GET /pacientes -> listado de pacientes (cualquier usuario autenticado) */
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pacientes", pacienteRepository.listarTodos());
        return "pacientes/lista";
    }

    /** GET /pacientes/nuevo -> formulario de registro (ADMIN y RECEPCION) */
    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "pacientes/formulario";
    }

    /** POST /pacientes -> guarda el paciente en memoria (ADMIN y RECEPCION) */
    @PostMapping
    public String guardar(@ModelAttribute Paciente paciente,
                          RedirectAttributes redirectAttributes,
                          Model model) {

        if (paciente.getNombre() == null || paciente.getNombre().isBlank()
                || paciente.getEspecie() == null || paciente.getEspecie().isBlank()
                || paciente.getDueno() == null || paciente.getDueno().isBlank()) {
            model.addAttribute("error", "Nombre, especie y dueno son obligatorios.");
            model.addAttribute("paciente", paciente);
            return "pacientes/formulario";
        }

        pacienteRepository.guardar(paciente);
        redirectAttributes.addFlashAttribute("mensaje",
                "Paciente '" + paciente.getNombre() + "' registrado correctamente.");
        return "redirect:/pacientes";
    }

    /** POST /pacientes/{id}/eliminar -> elimina el paciente (solo ADMIN) */
    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        pacienteRepository.eliminarPorId(id);
        redirectAttributes.addFlashAttribute("mensaje", "Paciente eliminado.");
        return "redirect:/pacientes";
    }
}
