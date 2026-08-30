package com.veterinaria.demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
            // ----------RUTAS PUBLICAS ----------
                .requestMatchers("/", "/home", "/login", "/acceso-denegado").permitAll()
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/nosotros").permitAll()

            //---------- PACIENTES (privado) ----------
            .requestMatchers("/pacientes/nuevo").hasAnyRole("ADMIN", "RECEPCION")
            .requestMatchers(HttpMethod.POST, "/pacientes").hasAnyRole("ADMIN", "RECEPCION")
            // Eliminar pacientes: solo ADMIN
            .requestMatchers(HttpMethod.POST, "/pacientes/*/eliminar").hasRole("ADMIN")
            // Ver el listado: cualquier usuario autenticado
            .requestMatchers("/pacientes/**").authenticated()

             // ---------- CITAS (privado) ----------
                // Programar citas: solo ADMIN y RECEPCION
                .requestMatchers("/citas/nueva").hasAnyRole("ADMIN", "RECEPCION")
                .requestMatchers(HttpMethod.POST, "/citas").hasAnyRole("ADMIN", "RECEPCION")
                // Cancelar citas: solo ADMIN
                .requestMatchers(HttpMethod.POST, "/citas/*/eliminar").hasRole("ADMIN")
                // Ver la agenda: cualquier usuario autenticado
                .requestMatchers("/citas/**").authenticated()

                // ---------- VETERINARIOS (privado) ----------
                                // ---------- VETERINARIOS (privado) ----------
                // TODO ALUMNOS (2 de 2): agregar aqui la regla que deja "/veterinarios/**"
                // accesible SOLO para el rol ADMIN. Pista: .hasRole("ADMIN")

            .requestMatchers("/veterinario/**").hasRole("ADMIN")

                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/login")
                .defaultSuccessUrl("/home", true)
                .permitAll()
            )
             .logout(logout -> logout
                .logoutSuccessUrl("/home?logout")
                .permitAll()
            )
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/acceso-denegado")
            );
        return http.build();
    }


    @Bean
    @Description("In memory Userdetails service registered since DB doesn't have user table ")
    public UserDetailsService users() {
        // The builder will ensure the passwords are encoded before saving in memory
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails recepcion = User.builder()
                .username("recepcion")
                .password(passwordEncoder().encode("recep123"))
                .roles("RECEPCION")
                .build();

        UserDetails veterinario = User.builder()
                .username("vet")
                .password(passwordEncoder().encode("vet123"))
                .roles("VETERINARIO")
                .build();

        return new InMemoryUserDetailsManager(admin, recepcion, veterinario);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}