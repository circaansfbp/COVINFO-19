package com.example.covinfo_19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.covinfo_19.servicios_web.ServicioWeb;
import com.example.covinfo_19.servicios_web.info_por_region.Region;
import com.example.covinfo_19.servicios_web.info_por_region.RegionesRWS;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home extends AppCompatActivity {
    private ServicioWeb servicio;
    private Button nationalStatisticsBtn;
    private Button regionStatistics;
    private TextInputEditText regionInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /**Se infla el botón que llevará a las estadísticas nacionales*/
        nationalStatisticsBtn = findViewById(R.id.estadisticas_nacionales);
        nationalStatisticsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initEstadisticasNacionales();
            }
        });

        /**Se llama a la librería para que se creen los objetos al hacer la llamada al servicio web.*/
        Retrofit retrofit = new Retrofit.Builder().baseUrl(" http://covid.unnamed-chile.com/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        /**Inyección de dependencia*/
        servicio = retrofit.create(ServicioWeb.class);

        /**Se infla el input en el que se ingresa la región*/
        regionInput = findViewById(R.id.región_input);

        /**Se infla el botón que hará la consulta.*/
        regionStatistics = findViewById(R.id.estadisticas_de_una_region);
        regionStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(regionInput.getText())) {
                    regionInput.setError("Debe ingresar una región!");

                } else checkValidRegion();
            }
        });
    }

    /**Método para ir a la actividad que mostrará las estadísticas nacionales.*/
    private void initEstadisticasNacionales() {
        Intent init = new Intent(this, ReporteDiario.class);
        startActivity(init);
        finish();
    }

    private void checkValidRegion() {
        final Call<RegionesRWS> respuesta = servicio.getAllRegions();
        respuesta.enqueue(new Callback<RegionesRWS>() {
            @Override
            public void onResponse(Call<RegionesRWS> call, Response<RegionesRWS> response) {
                if (response != null && response.body() != null) {
                    RegionesRWS datos = response.body();

                    for (int i=0; i<datos.getRegiones().length; i++) {
                        if (TextUtils.equals(regionInput.getText().toString().toLowerCase(), datos.getRegiones()[i].getNombre().toLowerCase())) {
                            Log.d("retrofit", "Match enconcontrada!");
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<RegionesRWS> call, Throwable t) {
                Log.d("retrofit", "Error: " + t.getMessage());
            }
        });
    }
}