package com.example.covinfo_19.comparativa_regiones;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;

import com.example.covinfo_19.R;
import com.example.covinfo_19.servicios_web.Region;
import com.example.covinfo_19.servicios_web.ServicioWeb;
import com.example.covinfo_19.servicios_web.respuestas.AllRegionRWS;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CasosAcumulados extends AppCompatActivity {
    private ServicioWeb servicio;
    private TextView fechaEstadisticasComparativas;
    private Button irCasosNuevosBtn;

    private AnyChartView casosAcumuladosAnyChartView;
    private Pie pie;

    private List<Region> casosAcumuladosSortingArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casos_acumulados);

        /**Se inflan los objetos.*/
        fechaEstadisticasComparativas = findViewById(R.id.fecha_estadisticas_comparativas);
        irCasosNuevosBtn = findViewById(R.id.top_casos_nuevos);
        irCasosNuevosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initCasosNuevos();
            }
        });

        /**Se llama a la librería para que se creen los objetos al hacer la llamada al servicio web.*/
        Retrofit retrofit = new Retrofit.Builder().baseUrl(" http://covid.unnamed-chile.com/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        /**Inyección de dependencia*/
        servicio = retrofit.create(ServicioWeb.class);

        getAllData();
    }

    /**Método para avanzar a la ventana que mostrará las 3 regiones con la mayor cantidad de casos nuevos en el día.*/
    private void initCasosNuevos() {
        Intent init = new Intent(this, CasosNuevos.class);
        startActivity(init);
        finish();
    }

    /**Método que realiza la petición al servicio web para obtener los datos de todas las regiones.*/
    private void getAllData() {
        final Call<AllRegionRWS> respuesta = servicio.getAllDataPerRegion();
        respuesta.enqueue(new Callback<AllRegionRWS>() {
            @Override
            public void onResponse(Call<AllRegionRWS> call, Response<AllRegionRWS> response) {
                if (response != null && response.body() != null) {
                    AllRegionRWS datos = response.body();

                    /**Se setea la fecha del día al que corresponden los datos.*/
                    fechaEstadisticasComparativas.setText(datos.getFecha());

                    /**Se obtienen los nombres y los casos acumulados de la respuesta del servicio web, se crean nuevos objetos de tipo Region,
                     * y estos son agregados a un nuevo Array que posteriormente será ordenado*/
                    for (int i=0; i<datos.getReporte().length; i++) {
                        casosAcumuladosSortingArray.add(new Region(datos.getReporte()[i].getAcumulado_total(), datos.getReporte()[i].getRegion()));
                    }

                    sortingFunction(casosAcumuladosSortingArray);

                    /**Se crea el pie chart que mostrará las tres regiones con la mayor cantidad de casos nuevos de COVID-19 del día.
                     * NOTA: Como equipo nos vimos obligados a solo mostrar 3 regiones, ya que con una mayor cantidad de datos el gráfico no carga.*/
                    casosAcumuladosAnyChartView = findViewById(R.id.acumulados_any_chart_view);
                    pie = AnyChart.pie();
                    List<DataEntry> chartData = new ArrayList<>();
                    chartData.add(new ValueDataEntry(casosAcumuladosSortingArray.get(0).getNombre(), casosAcumuladosSortingArray.get(0).getId()));
                    chartData.add(new ValueDataEntry(casosAcumuladosSortingArray.get(1).getNombre(), casosAcumuladosSortingArray.get(1).getId()));
                    chartData.add(new ValueDataEntry(casosAcumuladosSortingArray.get(2).getNombre(), casosAcumuladosSortingArray.get(2).getId()));
                    //chartData.add(new ValueDataEntry(casosAcumuladosSortingArray.get(3).getNombre(), casosAcumuladosSortingArray.get(3).getId()));

                    pie.data(chartData);
                    pie.title("Las tres regiones con mayor cantidad de casos acumulados a la fecha (%)");
                    pie.labels().position("outside");

                    pie.legend().title().enabled(true);
                    pie.legend().title()
                            .text("Regiones")
                            .padding(0d, 0d, 10d, 0d);

                    pie.legend()
                            .position("center-bottom")
                            .itemsLayout(LegendLayout.HORIZONTAL)
                            .align(Align.CENTER);

                    casosAcumuladosAnyChartView.setChart(pie);

                }
            }

            @Override
            public void onFailure(Call<AllRegionRWS> call, Throwable t) {
                Log.d("retrofit", "Error: " + t.getMessage());
            }
        });
    }

    /**Método para ordenar un arreglo de forma descendente*/
    private void sortingFunction (List<Region> regionList) {
        int tempID;
        String tempName;
        for (int i=0; i<regionList.size(); i++) {
            for (int j=i+1; j<regionList.size(); j++) {
                if (regionList.get(i).getId() <= regionList.get(j).getId()) {
                    tempID = regionList.get(i).getId();
                    tempName = regionList.get(i).getNombre();

                    regionList.get(i).setId(regionList.get(j).getId());
                    regionList.get(i).setNombre(regionList.get(j).getNombre());

                    regionList.get(j).setId(tempID);
                    regionList.get(j).setNombre(tempName);
                }
            }
        }
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