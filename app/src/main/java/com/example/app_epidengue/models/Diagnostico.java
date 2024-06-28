package com.example.app_epidengue.models;

import lombok.Data;

@Data
public class Diagnostico {
    private int idDiagnostico;
    private String nombreDiagnostico;
    private String tipoDeDiagnostico;
    private float fiebre;
    private String sintomas;
    private String lugarPInfeccion;
}
