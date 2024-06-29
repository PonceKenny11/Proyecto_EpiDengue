package com.example.app_epidengue.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class FichaDengue {
    private int idFicha;
    private String idPaciente;
    private int historiaClinica;
    private String establecimientoDeSalud;
    private int idDiagnostico;
    private int idLugarInfeccion;
    private String fechaInicioSintoma;
    private int semanaEpidemiologica;
    private String fechaHospitalizacion;
    private String fechaMuestraLaboratorio;

    public int getHistoriaClinica() {
        return historiaClinica;
    }
}
