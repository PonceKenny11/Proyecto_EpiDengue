package com.example.app_epidengue;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Connection;

public class MainActivity extends AppCompatActivity {

    EditText usuario, contraseña;
    TextView lblregistrar;
    Button btningresar;
    Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.btningresar), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Vincular vistas
        usuario = findViewById(R.id.txtusuario);
        contraseña = findViewById(R.id.txtcontra);
        btningresar = findViewById(R.id.btningresar);
        lblregistrar = findViewById(R.id.lblregistrar);

        // Configurar el botón de Ingresar

    }

    public void IngresarMenu(View view){
        String user = usuario.getText().toString();
        String pass = contraseña.getText().toString();

        if (user.isEmpty() || pass.isEmpty()) {
            Toast.makeText(MainActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            // Lógica de autenticación
            if (user.equals("epi") && pass.equals("epi2024")) {
                Toast.makeText(MainActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                // Navegar a otra actividad si es necesario
                Intent intent = new Intent(MainActivity.this, Home.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void limpiarCampos(){
        usuario.setText("");
        contraseña.setText("");
        usuario.requestFocus();
    }







    ////////////////////////hola compañeros

}
