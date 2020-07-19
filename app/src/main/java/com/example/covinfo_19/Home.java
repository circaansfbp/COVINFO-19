package com.example.covinfo_19;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.covinfo_19.comparativa_regiones.CasosAcumulados;
import com.example.covinfo_19.servicios_web.ServicioWeb;
import com.example.covinfo_19.servicios_web.respuestas.RegionesRWS;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home extends AppCompatActivity {
    private ServicioWeb servicio;
    private Button nationalStatisticsBtn;
    private Button regionalStatisticsBtn;
    private Spinner listaRegiones;
    private List<String> nombreRegiones = new ArrayList<>();
    private int regionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /**Se infla el botón que llevará a las estadísticas de todas las regiones.*/
        regionalStatisticsBtn = findViewById(R.id.estadisticas_regionales);
        regionalStatisticsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initEstadisticasAllRegions();
            }
        });

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

        /**Se infla el spinner*/
        listaRegiones = findViewById(R.id.lista_regiones);

        /**Se llena el arreglo que contendrá las opciones del spinner a través del método fillSpinnerArray(), se crea el spinner
         * y, al seleccionar un elemento de este y que no sea el valor por default (posición cero que entrega msje de ayuda al usuario),
         * se llama al método checkRegionID()*/
        fillSpinnerArray();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombreRegiones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listaRegiones.setAdapter(adapter);

        listaRegiones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!(adapterView.getSelectedItem().toString().equalsIgnoreCase(nombreRegiones.get(0)))) {
                    checkRegionID(adapterView);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
    }

    /**Método para ir a la actividad que mostrará las estadísticas nacionales.*/
    private void initEstadisticasNacionales() {
        Intent init = new Intent(this, ReporteDiario.class);
        startActivity(init);
        finish();
    }

    /**Método para ir a la actividad que mostrará las estadísticas de la región que sea seleccionada en el spinner.*/
    private void initEstadisticasDeUnaRegion() {
        Intent init = new Intent(this, ReporteDeUnaRegion.class);
        init.putExtra("id", regionID);
        startActivity(init);
        finish();
    }

    /**Método para ir a la actividad que mostrará las estadísticas comparativas de todas las regiones del país.*/
    private void initEstadisticasAllRegions() {
        Intent init = new Intent(this, CasosAcumulados.class);
        startActivity(init);
        finish();
    }

    /**Método para llenar el arreglo que contendrá las opciones del spinner.*/
    private void fillSpinnerArray() {
        nombreRegiones.add("Seleccione una región...");
        nombreRegiones.add("Arica y Parinacota");
        nombreRegiones.add("Tarapacá");
        nombreRegiones.add("Antofagasta");
        nombreRegiones.add("Atacama");
        nombreRegiones.add("Coquimbo");
        nombreRegiones.add("Valparaíso");
        nombreRegiones.add("Metropolitana");
        nombreRegiones.add("O'Higgins");
        nombreRegiones.add("Maule");
        nombreRegiones.add("Ñuble");
        nombreRegiones.add("Biobío");
        nombreRegiones.add("Araucania");
        nombreRegiones.add("Los Ríos");
        nombreRegiones.add("Los Lagos");
        nombreRegiones.add("Aysén");
        nombreRegiones.add("Magallanes");
    }

    /**Método que rescata el ID de la región que fue seleccionada en el drop down, comparando dicha selección con los datos obtenidos
     * en la petición al servicio web.*/
    private void checkRegionID(AdapterView<?> adapterView) {
        final Call<RegionesRWS> respuesta = servicio.getAllRegions();
        respuesta.enqueue(new Callback<RegionesRWS>() {
            @Override
            public void onResponse(Call<RegionesRWS> call, Response<RegionesRWS> response) {
                if (response != null && response.body() != null) {
                    RegionesRWS datos = response.body();

                    for (int i=0; i<datos.getRegiones().length; i++) {
                        if (adapterView.getSelectedItem().toString().equalsIgnoreCase(datos.getRegiones()[i].getNombre())) {
                            regionID = datos.getRegiones()[i].getId();
                            initEstadisticasDeUnaRegion();
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

    /**Al presionar el botón para ir atrás, confirma si realmente desea salir de la aplicación.*/
    @Override
    public void onBackPressed() {
        AlertDialog.Builder msg = new AlertDialog.Builder(this);
        msg.setTitle("Salir de la aplicación");
        msg.setMessage("¿Está seguro de querer salir de la aplicación?");

        msg.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        msg.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog showMsg = msg.create();
        showMsg.show();
    }
}