package com.example.covinfo_19;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;

import com.example.covinfo_19.servicios_web.ServicioWeb;
import com.example.covinfo_19.servicios_web.respuestas.RespuestaWS;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReporteDeUnaRegion extends AppCompatActivity {
    private ServicioWeb servicio;
    private int regionID;

    private TextView nombreRegionSeleccionada;
    private TextView fechaReporteRegion;
    private TextView totalCasosRegion;
    private TextView totalCasosNuevos;
    private TextView fallecidosRegion;
    private TextView casosActivosRegion;

    private AnyChartView anyChartView;
    private Pie pie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_de_una_region);

        /**Se llama a la librería para que se creen los objetos al hacer la llamada al servicio web.*/
        Retrofit retrofit = new Retrofit.Builder().baseUrl(" http://covid.unnamed-chile.com/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        /**Inyección de dependencia*/
        servicio = retrofit.create(ServicioWeb.class);

        /**Se obtiene el valor del ID desde la actividad anterior, para que sea entregada como parámetro en la URL al hacer
         * la petición al servicio web.*/
        Intent intent = getIntent();
        regionID = intent.getIntExtra("id", 0);

        /**Se inflan los objetos.*/
        nombreRegionSeleccionada = findViewById(R.id.nombre_region_seleccionada);
        fechaReporteRegion = findViewById(R.id.fecha_reporte_region);
        totalCasosRegion = findViewById(R.id.total_casos_region);
        totalCasosNuevos = findViewById(R.id.total_casos_nuevos_region);
        fallecidosRegion = findViewById(R.id.fallecidos_region);
        casosActivosRegion = findViewById(R.id.casos_activos_region);

        requestRegionData();
    }

    private void requestRegionData() {
        if (regionID != 0) {
            final Call<RespuestaWS> respuesta = servicio.getDataForSpecifiedRegion(regionID);
            respuesta.enqueue(new Callback<RespuestaWS>() {
                @Override
                public void onResponse(Call<RespuestaWS> call, Response<RespuestaWS> response) {
                    if (response != null && response.body() != null) {
                        RespuestaWS datos = response.body();

                        /**Se setean los textos en los TextView de acuerdo a los datos recibidos en la respuesta del servicio web.*/
                        String nombreRegion = datos.getInfo();
                        nombreRegion = nombreRegion.replace("Región: ", "");
                        nombreRegionSeleccionada.setText(nombreRegion);

                        fechaReporteRegion.setText(datos.getFecha());
                        totalCasosRegion.setText(String.valueOf(datos.getReporte().getAcumulado_total()));
                        totalCasosNuevos.setText(String.valueOf(datos.getReporte().getCasos_nuevos_total()));
                        fallecidosRegion.setText(String.valueOf(datos.getReporte().getFallecidos()));
                        casosActivosRegion.setText(String.valueOf(datos.getReporte().getCasos_activos_confirmados()));

                        createChart(datos);
                    }
                }

                @Override
                public void onFailure(Call<RespuestaWS> call, Throwable t) {
                    Log.d("retrofit", "Error: " + t.getMessage());
                }
            });
        }
    }

    /**Crea un diagrama Pie que encapsula la distribución de casos nuevos, mostrando el porcentaje de casos nuevos con síntomas, sin síntomas
     * y los casos nuevos sin notificar.*/
    private void createChart(RespuestaWS datos) {
        anyChartView = findViewById(R.id.any_chart_view);
        pie = AnyChart.pie();

        List<DataEntry> chartData = new ArrayList<>();
        chartData.add(new ValueDataEntry("Casos nuevos con síntomas", datos.getReporte().getCasos_nuevos_csintomas()));
        chartData.add(new ValueDataEntry("Casos nuevos sin síntomas", datos.getReporte().getCasos_nuevos_ssintomas()));
        chartData.add(new ValueDataEntry("Casos nuevos sin notificar", datos.getReporte().getCasos_nuevos_snotificar()));

        pie.data(chartData);
        pie.title("Distribución de casos nuevos en la región (%)");
        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Casos")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        anyChartView.setChart(pie);
    }

    /**creacion e inflacion del menu*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    /**casos de seleccion del menu*/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.activity1:
                Intent intent = new Intent(ReporteDeUnaRegion.this, Home.class);
                startActivity(intent);
                return false;
            case R.id.activity2:
                Intent intent2 = new Intent(ReporteDeUnaRegion.this, ReporteDiario.class);
                startActivity(intent2);
                return false;
            case R.id.activity3:
                Intent intent3 = new Intent(ReporteDeUnaRegion.this, ReporteDeUnaRegion2.class);
                startActivity(intent3);
                return false;
            case R.id.activity4:
                Intent intent4 = new Intent(ReporteDeUnaRegion.this, ReporteDeUnaRegion2.class);
                /**cambiar por clase reporte de todas las regiones*/
                startActivity(intent4);
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}