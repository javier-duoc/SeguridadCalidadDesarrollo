package com.veterinaria.demo.model;

/**
 * Representa una mascota atendida en la veterinaria.
 * Se almacena en memoria (ver PacienteRepository), no hay base de datos.
 */
public class Paciente {

    private Long id;
    private String nombre;
    private String especie;
    private String raza;
    private Integer edad;
    private String dueno;

    public Paciente() {
    }

    public Paciente(Long id, String nombre, String especie, String raza, Integer edad, String dueno) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.dueno = dueno;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getDueno() {
        return dueno;
    }

    public void setDueno(String dueno) {
        this.dueno = dueno;
    }
}
