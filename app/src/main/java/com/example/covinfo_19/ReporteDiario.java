package com.example.covinfo_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

import com.example.covinfo_19.comparativa_regiones.CasosAcumulados;
import com.example.covinfo_19.servicios_web.ServicioWeb;
import com.example.covinfo_19.servicios_web.respuestas.RespuestaWS;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReporteDiario extends AppCompatActivity {
    private ServicioWeb servicio;

    private TextView fechaReporte;
    private TextView casosTotales;
    private TextView casosNuevosTotales;
    private TextView fallecidos;
    private TextView casosActivosConfirmados;

    private AnyChartView anyChartView;
    private Pie pie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_diario);

        /**Se inflan los objetos*/
        fechaReporte = findViewById(R.id.fecha_reporte);
        casosTotales = findViewById(R.id.total_casos);
        casosNuevosTotales = findViewById(R.id.casos_nuevos_totales);
        fallecidos = findViewById(R.id.fallecidos);
        casosActivosConfirmados = findViewById(R.id.casos_activos_confirmados);

        /**Se llama a la librería para que se creen los objetos al hacer la llamada al servicio web.*/
        Retrofit retrofit = new Retrofit.Builder().baseUrl(" http://covid.unnamed-chile.com/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        /**Inyección de dependencia*/
        servicio = retrofit.create(ServicioWeb.class);

        /**Se llama al método requestData()*/
        requestData();
    }

    /**Método que realiza la petición al servicio web para recuperar los datos correspondientes al reporte diario.*/
    private void requestData() {
        final Call<RespuestaWS> respuesta = servicio.getNationalData();
        respuesta.enqueue(new Callback<RespuestaWS>() {
            @Override
            public void onResponse(Call<RespuestaWS> call, Response<RespuestaWS> response) {
                if (response != null && response.body() != null) {
                    RespuestaWS datos = response.body();

                    /**Se setean los textos en los TextView de acuerdo a los datos recibidos en la respuesta del servicio web.*/
                    fechaReporte.setText(datos.getFecha() + "*");
                    casosTotales.setText(String.valueOf(datos.getReporte().getAcumulado_total()));
                    casosNuevosTotales.setText(String.valueOf(datos.getReporte().getCasos_nuevos_total()));
                    fallecidos.setText(String.valueOf(datos.getReporte().getFallecidos()));
                    casosActivosConfirmados.setText(String.valueOf(datos.getReporte().getCasos_activos_confirmados()));

                    /**Se llama al método createChart().*/
                    createChart(datos);
                }
            }

            @Override
            public void onFailure(Call<RespuestaWS> call, Throwable t) {
                Log.d("retrofit", "Error: " + t.getMessage());
            }
        });
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
        pie.title("Distribución de casos nuevos a la fecha (%)");
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

    /**se infla el menu*/
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
                Intent intent = new Intent(ReporteDiario.this, Home.class);
                startActivity(intent);
                finish();
                return false;
            case R.id.activity2:
                Intent intent2 = new Intent(ReporteDiario.this, ReporteDiario.class);
                startActivity(intent2);
                finish();
                return false;
            case R.id.activity3:
                Intent intent3 = new Intent(ReporteDiario.this, CasosAcumulados.class);
                startActivity(intent3);
                finish();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}