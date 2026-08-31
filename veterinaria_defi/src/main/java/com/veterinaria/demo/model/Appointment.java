package com.veterinaria.demo.model;

public class Appointment {

    private String paciente;
    private String fechaHora;   // formato: "2024-12-20 10:30"
    private String motivo;
    private String veterinario;

    public Appointment() {}

    public Appointment(String paciente, String fechaHora, String motivo, String veterinario) {
        this.paciente = paciente;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.veterinario = veterinario;
    }

    public String getPaciente()          { return paciente; }
    public void setPaciente(String p)    { this.paciente = p; }

    public String getFechaHora()         { return fechaHora; }
    public void setFechaHora(String f)   { this.fechaHora = f; }

    public String getMotivo()            { return motivo; }
    public void setMotivo(String m)      { this.motivo = m; }

    public String getVeterinario()       { return veterinario; }
    public void setVeterinario(String v) { this.veterinario = v; }
}
