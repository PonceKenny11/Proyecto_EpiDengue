package com.example.app_epidengue.models;

import lombok.Data;

@Data
public class LugarInfeccion {
    private int idLugarInfeccion;
    private String direccionPI;
    private String departamento;
    private String provincia;
    private String distrito;
    private double latitud;
    private double longitud;
}
