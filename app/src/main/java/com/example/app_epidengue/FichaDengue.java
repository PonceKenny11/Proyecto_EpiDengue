package com.example.app_epidengue;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_epidengue.Repository.FichaDengueDB;
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

        inicilizandoData();
    }

    private void inicilizandoData(){
        txtFHospitalizados = findViewById(R.id.txtFHospitalizacion);
        txtFMuestraLabortorio = findViewById(R.id.txtFmuestraLab);
        txtFIniSintomas = findViewById(R.id.txtFIniSintom);
        NroHC = findViewById(R.id.txtNoHC);
        NroSE= findViewById(R.id.nroSE);
        cboEstablecimiento = findViewById(R.id.cboESalud);

        // Obtener la fecha actual
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


        String idPaciente = fichaDB.getLastPacienteId();
        int idDiagnostico = fichaDB.getLastDiagnosticoId();
        int idLugarInfeccion = fichaDB.getLastLugarInfeccionId();

        int nroHC = Integer.parseInt(historiaClinicaStr);
        int nroSeEpi = Integer.parseInt(semanaEpidemiologicaStr);

        validar.validarFichaDengue(txtFHospitalizados,txtFMuestraLabortorio,txtFIniSintomas, idDiagnostico, idLugarInfeccion, idPaciente);
        boolean isRegister = fichaDB.insertFichaPaciente(idPaciente,idDiagnostico,idLugarInfeccion, nroHC, establecimientoSalud,
                fechaInicioSintomas,nroSeEpi,fechaHospitalizacion,fechaMuestraLaboratorio);

        if (isRegister){
            Toast.makeText(this, "Ficha Dengue Completada!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "ERROR - NO SE PUDO REGISTRAR TBL  ", Toast.LENGTH_SHORT).show();
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
        // Establecer el primer día de la semana al domingo
        date.setFirstDayOfWeek(Calendar.SUNDAY);
        // Obtener la semana del año para la fecha dada
        int weekOfYear = date.get(Calendar.WEEK_OF_YEAR);
        // Ajustar la semana epidemiológica si la semana comienza antes del primer jueves del año
        if (date.get(Calendar.MONTH) == Calendar.JANUARY && weekOfYear > 50) {
            weekOfYear = 1;
        }
        return weekOfYear;
    }
    private void showDatePickerDialog(final EditText editText) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(selectedYear, selectedMonth, selectedDay);

                // Formato de fecha
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                editText.setText(sdf.format(selectedDate.getTime()));

                if (editText == txtFIniSintomas) {
                    int epidemiologicalWeek = getEpidemiologicalWeek(selectedDate);
                    String mostrarSEPI =String.valueOf(epidemiologicalWeek) ;
                    NroSE.setText(mostrarSEPI);
                }
            }
        }, año, mes, dia);
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime()); // Establecer la fecha máxima al día actual
        datePickerDialog.show();
    }
}