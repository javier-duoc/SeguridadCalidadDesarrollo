package com.veterinaria.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Rutas publicas del sistema: portada y login.
 */

@Controller
public class HomeController {

    private static final String NOMBRE_APP = "VetCare - Sistema de Gestion Veterinaria";

    @GetMapping({"/", "/home" })
    public String home (Model model) {
        model.addAttribute("name", NOMBRE_APP);
        return "home";
    }

    @GetMapping("/login")
    public String login (Model model) {
        model.addAttribute("name", NOMBRE_APP);
        return "login";
    }

    /**
     * Pagina mostrada cuando el usuario esta autenticado pero su rol no basta (error 403).
     *
     * Se mapea con @RequestMapping sin indicar el metodo HTTP a proposito: Spring Security
     * hace un "forward" a esta URL conservando el metodo original, asi que un POST denegado
     * (por ejemplo eliminar un paciente sin ser ADMIN) tambien debe poder entrar aqui.
     */

    @RequestMapping("/acceso-denegado")
    public String accesoDenegado() {
        return "acceso-denegado";
    }

      @GetMapping("/nosotros")
    public String nosotros (Model model) {
        model.addAttribute("name", NOMBRE_APP);
        return "nosotros";
    }
}





