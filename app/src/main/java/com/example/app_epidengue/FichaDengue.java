package com.example.app_epidengue;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_epidengue.Repository.FichaDengueDB;
import com.example.app_epidengue.validaciones.NotificacionesDengue;
import com.example.app_epidengue.validaciones.Validaciones;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FichaDengue extends AppCompatActivity {



    private EditText txtFHospitalizados,txtFMuestraLabortorio,txtFIniSintomas,NroSE,NroHC;
    private Spinner cboEstablecimiento;
    private FichaDengueDB fichaDB;
    private Validaciones validar;

    private NotificacionesDengue notiD;
    private int año, mes, dia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ficha_dengue);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.btningresar), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        notiD = new NotificacionesDengue(this);
        inicilizandoData();
    }

    private void inicilizandoData(){
        txtFHospitalizados = findViewById(R.id.txtFHospitalizacion);
        txtFMuestraLabortorio = findViewById(R.id.txtFmuestraLab);
        txtFIniSintomas = findViewById(R.id.txtFIniSintom);
        NroHC = findViewById(R.id.txtNoHC);
        NroSE = findViewById(R.id.nroSE);
        cboEstablecimiento = findViewById(R.id.cboESalud);

        final Calendar calendar = Calendar.getInstance();
        año = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);

        ArrayAdapter<CharSequence> adapterEES = ArrayAdapter.createFromResource(this,
                R.array.establecimientos_de_salud, android.R.layout.simple_spinner_item);

        adapterEES.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboEstablecimiento.setAdapter(adapterEES);

        fichaDB = new FichaDengueDB(this);
        validar = new Validaciones(this);
    }

    public void insertandoFichaFinal(View view){
        String historiaClinicaStr = NroHC.getText().toString().trim();
        String establecimientoSalud = cboEstablecimiento.getSelectedItem().toString();
        String fechaInicioSintomas = txtFIniSintomas.getText().toString().trim();
        String semanaEpidemiologicaStr = NroSE.getText().toString().trim();
        String fechaHospitalizacion = txtFHospitalizados.getText().toString().trim();
        String fechaMuestraLaboratorio = txtFMuestraLabortorio.getText().toString().trim();

        if (!validar.validarFichaDengue(txtFIniSintomas, txtFMuestraLabortorio, txtFHospitalizados, fichaDB.getLastDiagnosticoId(), fichaDB.getLastLugarInfeccionId(), fichaDB.getLastPacienteId())) {
            return;
        }

        int nroHC, nroSeEpi;
        try {
            nroHC = Integer.parseInt(historiaClinicaStr);
            nroSeEpi = Integer.parseInt(semanaEpidemiologicaStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, ingrese valores válidos para historia clínica y semana epidemiológica", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isRegister = fichaDB.insertFichaPaciente(fichaDB.getLastPacienteId(), fichaDB.getLastDiagnosticoId(), fichaDB.getLastLugarInfeccionId(), nroHC, establecimientoSalud,
                fechaInicioSintomas, nroSeEpi, fechaHospitalizacion, fechaMuestraLaboratorio);


        if (isRegister) {
            notiD.sendNotification(historiaClinicaStr,"Ficha registrada para el paciente con DNI: ","Nueva Ficha de Dengue Registrada");
            Toast.makeText(this, "Ficha Dengue Completada!", Toast.LENGTH_SHORT).show();
            Intent instanciar4 = new Intent(this, Home.class);
            startActivity(instanciar4);
            finish();
        } else {
            Toast.makeText(this, "ERROR - NO SE PUDO REGISTRAR", Toast.LENGTH_SHORT).show();
        }
    }

    public void showValiDate1(View view) {
        showDatePickerDialog(txtFIniSintomas);
    }

    public void showValiDate2(View view) {
        showDatePickerDialog(txtFMuestraLabortorio);
    }

    public void showValiDate3(View view) {
        showDatePickerDialog(txtFHospitalizados);
    }

    private int getEpidemiologicalWeek(Calendar date) {
        date.setFirstDayOfWeek(Calendar.SUNDAY);
        int weekOfYear = date.get(Calendar.WEEK_OF_YEAR);
        if (date.get(Calendar.MONTH) == Calendar.JANUARY && weekOfYear > 50) {
            weekOfYear = 1;
        }
        return weekOfYear;
    }

    private void showDatePickerDialog(final EditText editText) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(selectedYear, selectedMonth, selectedDay);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            editText.setText(sdf.format(selectedDate.getTime()));

            if (editText == txtFIniSintomas) {
                int epidemiologicalWeek = getEpidemiologicalWeek(selectedDate);
                NroSE.setText(String.valueOf(epidemiologicalWeek));
            }
        }, año, mes, dia);
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }





}