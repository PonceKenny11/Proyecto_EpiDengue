package com.example.app_epidengue.models;

import lombok.Data;

@Data
public class Paciente {
    private String DNI;
    private String nombreCompleto;
    private int edad;
    private String sexo;
    private int nroTelefono;
}
