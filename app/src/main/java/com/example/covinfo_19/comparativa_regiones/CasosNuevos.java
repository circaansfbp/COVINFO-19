package com.example.covinfo_19.comparativa_regiones;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
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

public class CasosNuevos extends AppCompatActivity {
    private ServicioWeb servicio;

    private Button volverCasosAcumuladosBtn;
    private Button irCasosActivos;
    private TextView fechaCasosNuevos;

    private AnyChartView nuevosAnyChartView;
    private Pie pie;

    private List<Region> casosNuevosArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casos_nuevos);

        /**Se inflan los objetos y  se setean los listeners de eventos.*/
        volverCasosAcumuladosBtn = findViewById(R.id.volver_casos_acumulados);
        volverCasosAcumuladosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToCasosAcumulados();
            }
        });

        irCasosActivos = findViewById(R.id.ir_casos_activos);
        irCasosActivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initCasosActivos();
            }
        });

        fechaCasosNuevos = findViewById(R.id.fecha_casos_nuevos);

        /**Se llama a la librería para que se creen los objetos al hacer la llamada al servicio web.*/
        Retrofit retrofit = new Retrofit.Builder().baseUrl(" http://covid.unnamed-chile.com/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        /**Inyección de dependencia*/
        servicio = retrofit.create(ServicioWeb.class);

        getAllData();
    }

    /**Para regresar a la estadística anterior (Casos Acumulados)*/
    private void backToCasosAcumulados() {
        Intent init = new Intent(this, CasosAcumulados.class);
        startActivity(init);
        finish();
    }

    /**Método para ir a la view que mostrará las 3 regiones con mayor cantidad de casos activos.*/
    private void initCasosActivos() {
        Intent init = new Intent(this, CasosActivosConfirmados.class);
        startActivity(init);
        finish();
    }

    /**Método que realiza la petición al servicio para obtener los datos de todas las regiones.*/
    private void getAllData() {
        final Call<AllRegionRWS> respuesta = servicio.getAllDataPerRegion();
        respuesta.enqueue(new Callback<AllRegionRWS>() {
            @Override
            public void onResponse(Call<AllRegionRWS> call, Response<AllRegionRWS> response) {
                if (response != null && response.body() != null) {
                    AllRegionRWS datos = response.body();

                    /**Se setea la fecha del día.*/
                    fechaCasosNuevos.setText(datos.getFecha());

                    /**Se obtienen los nombres y los casos nuevos de la respuesta del servicio web, se crean nuevos objetos de tipo Region,
                     * y estos son agregados a un nuevo Array que posteriormente será ordenado.*/
                    for (int i=0; i<datos.getReporte().length; i++) {
                        casosNuevosArray.add(new Region(datos.getReporte()[i].getCasos_nuevos_total(), datos.getReporte()[i].getRegion()));
                    }
                    sortingFunction(casosNuevosArray);

                    /**Se crea el pie chart que mostrará las tres regiones con la mayor cantidad de casos nuevos de COVID-19 del día.
                     * * NOTA: Como equipo nos vimos obligados a solo mostrar 3 regiones, ya que con una mayor cantidad de datos el gráfico no carga.*/
                    nuevosAnyChartView = findViewById(R.id.nuevos_any_chart_view);
                    pie = AnyChart.pie();

                    List<DataEntry> data = new ArrayList<>();
                    data.add(new ValueDataEntry(casosNuevosArray.get(0).getNombre(), casosNuevosArray.get(0).getId()));
                    data.add(new ValueDataEntry(casosNuevosArray.get(1).getNombre(), casosNuevosArray.get(1).getId()));
                    data.add(new ValueDataEntry(casosNuevosArray.get(2).getNombre(), casosNuevosArray.get(2).getId()));

                    pie.data(data);
                    pie.title("Las tres regiones con mayor cantidad de casos nuevos el día de hoy (%)");
                    pie.labels().position("outside");

                    pie.legend().title().enabled(true);
                    pie.legend().title()
                            .text("Regiones")
                            .padding(0d, 0d, 10d, 0d);

                    pie.legend()
                            .position("center-bottom")
                            .itemsLayout(LegendLayout.HORIZONTAL)
                            .align(Align.CENTER);

                    nuevosAnyChartView.setChart(pie);
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