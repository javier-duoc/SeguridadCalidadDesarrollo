package com.veterinaria.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.veterinaria.demo.repository.VeterinarioRepository;

/**
 * =========================================================================
 * TODO ALUMNOS (2 de 2) - RUTA PRIVADA "/veterinarios"
 * -------------------------------------------------------------------------
 * Deben crear la pagina privada que muestra el equipo veterinario de la
 * clinica. SOLO el usuario con rol ADMIN puede verla; un usuario con rol
 * RECEPCION o VETERINARIO debe recibir un error 403 (Acceso denegado).
 *
 * Los datos YA existen: VeterinarioRepository.listarTodos() devuelve la
 * lista de veterinarios (id, nombre, especialidad). No hay que crear datos.
 *
 * Pasos:
 *   1. Descomentar el metodo listar() de mas abajo (y su @GetMapping).
 *   2. Crear la plantilla src/main/resources/templates/veterinarios/lista.html
 *      con una tabla de 3 columnas. Copien la estructura de
 *      templates/pacientes/lista.html, es practicamente igual.
 *   3. Proteger la URL en WebSecurityConfig con
 *      .requestMatchers("/veterinarios/**").hasRole("ADMIN")
 *
 * Como comprobar que quedo bien:
 *   - Entrar con admin/admin123    -> se ve la tabla.
 *   - Entrar con recepcion/recep123 -> pagina de acceso denegado (403).
 *   - Sin iniciar sesion            -> redirige al login.
 * =========================================================================
 */
@Controller
public class VeterinarioController {

    private final VeterinarioRepository veterinarioRepository;

    public VeterinarioController(VeterinarioRepository veterinarioRepository) {
        this.veterinarioRepository = veterinarioRepository;
    }

    // TODO ALUMNOS: descomentar y completar
    //
    @GetMapping("/Veterinario")
    public String listar(Model model) {
    model.addAttribute("veterinarios", veterinarioRepository.listarTodos());
    return "veterinarios/lista";
    }
}
