package com.example.covinfo_19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {
    private Button nationalStatisticsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        nationalStatisticsBtn = findViewById(R.id.estadisticas_nacionales);
        nationalStatisticsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("prueba", "El botón fue presionado!");
                initEstadisticasNacionales();
            }
        });
    }

    /**Método para ir a la actividad que mostrará las estadísticas nacionales.*/
    private void initEstadisticasNacionales() {
        Intent init = new Intent(this, ReporteDiario.class);
        startActivity(init);
        finish();
    }
}