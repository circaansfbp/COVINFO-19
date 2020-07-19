package com.example.covinfo_19.comparativa_regiones;

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
import com.example.covinfo_19.Home;
import com.example.covinfo_19.R;
import com.example.covinfo_19.ReporteDiario;
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

public class CasosActivosConfirmados extends AppCompatActivity {
    private ServicioWeb servicio;

    private TextView fechaCasosActivos;
    private Button volverCasosNuevos;
    private Button irFallecidos;

    private TextView nombreCuartoLugar; private TextView cantidadCuartoLugar; private TextView nombreQuintoLugar;
    private TextView cantidadQuintoLugar; private TextView nombreSextoLugar; private TextView cantidadSextoLugar;
    private TextView nombreSeptimoLugar; private TextView cantidadSeptimoLugar; private TextView nombreOctavoLugar;
    private TextView cantidadOctavoLugar; private TextView nombreNovenoLugar; private TextView cantidadNovenoLugar;
    private TextView nombreDecimoLugar; private TextView cantidadDecimoLugar; private TextView nombreOnceavoLugar;
    private TextView cantidadOnceavoLugar; private TextView nombreDoceavoLugar; private TextView cantidadDoceavoLugar;
    private TextView nombreTreceavoLugar; private TextView cantidadTreceavoLugar; private TextView nombreCatorceavoLugar;
    private TextView cantidadCatorceavoLugar; private TextView nombreQuinceavoLugar; private TextView cantidadQuinceavoLugar;
    private TextView nombreDieciseisavoLugar; private TextView cantidadDieciseisavoLugar;

    private AnyChartView activosAnyChartView;
    private Pie pie;

    private List<Region> casosActivosArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casos_activos_confirmados);

        fechaCasosActivos = findViewById(R.id.fecha_casos_activos);

        nombreCuartoLugar = findViewById(R.id.nombre_cuarto_lugar_activos_confirmados);
        cantidadCuartoLugar = findViewById(R.id.cantidad_cuarto_lugar_activos_confirmados);
        nombreQuintoLugar = findViewById(R.id.nombre_quinto_lugar_activos_confirmados);
        cantidadQuintoLugar = findViewById(R.id.cantidad_quinto_lugar_activos_confirmados);
        nombreSextoLugar = findViewById(R.id.nombre_sexto_lugar_activos_confirmados);
        cantidadSextoLugar = findViewById(R.id.cantidad_sexto_lugar_activos_confirmados);
        nombreSeptimoLugar = findViewById(R.id.nombre_septimo_lugar_activos_confirmados);
        cantidadSeptimoLugar = findViewById(R.id.cantidad_septimo_lugar_activos_confirmados);
        nombreOctavoLugar = findViewById(R.id.nombre_octavo_lugar_activos_confirmados);
        cantidadOctavoLugar = findViewById(R.id.cantidad_octavo_lugar_activos_confirmados);
        nombreNovenoLugar = findViewById(R.id.nombre_noveno_lugar_activos_confirmados);
        cantidadNovenoLugar = findViewById(R.id.cantidad_noveno_lugar_activos_confirmados);
        nombreDecimoLugar = findViewById(R.id.nombre_decimo_lugar_activos_confirmados);
        cantidadDecimoLugar = findViewById(R.id.cantidad_decimo_lugar_activos_confirmados);
        nombreOnceavoLugar = findViewById(R.id.nombre_onceavo_lugar_activos_confirmados);
        cantidadOnceavoLugar = findViewById(R.id.cantidad_onceavo_lugar_activos_confirmados);
        nombreDoceavoLugar = findViewById(R.id.nombre_doceavo_lugar_activos_confirmados);
        cantidadDoceavoLugar = findViewById(R.id.cantidad_doceavo_lugar_activos_confirmados);
        nombreTreceavoLugar = findViewById(R.id.nombre_treceavo_lugar_activos_confirmados);
        cantidadTreceavoLugar = findViewById(R.id.cantidad_treceavo_lugar_activos_confirmados);
        nombreCatorceavoLugar = findViewById(R.id.nombre_catorceavo_lugar_activos_confirmados);
        cantidadCatorceavoLugar = findViewById(R.id.cantidad_catorceavo_lugar_activos_confirmados);
        nombreQuinceavoLugar = findViewById(R.id.nombre_quinceavo_lugar_activos_confirmados);
        cantidadQuinceavoLugar = findViewById(R.id.cantidad_quinceavo_lugar_activos_confirmados);
        nombreDieciseisavoLugar = findViewById(R.id.nombre_dieciseisavo_lugar_activos_confirmados);
        cantidadDieciseisavoLugar = findViewById(R.id.cantidad_dieciseisavo_lugar_activos_confirmados);

        volverCasosNuevos = findViewById(R.id.volver_casos_nuevos);
        volverCasosNuevos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToCasosNuevos();
            }
        });

        irFallecidos = findViewById(R.id.ir_fallecidos);
        irFallecidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initFallecidos();
            }
        });

        /**Se llama a la librería para que se creen los objetos al hacer la llamada al servicio web.*/
        Retrofit retrofit = new Retrofit.Builder().baseUrl(" http://covid.unnamed-chile.com/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        /**Inyección de dependencia*/
        servicio = retrofit.create(ServicioWeb.class);

        getAllData();
    }

    /**Para regresar a la estadística anterior (Casos Nuevos)*/
    private void backToCasosNuevos() {
        Intent init = new Intent(this, CasosNuevos.class);
        startActivity(init);
        finish();
    }

    /**Método para ir a la view que mostrará las 3 regiones con mayor cantidad de fallecidos.*/
    private void initFallecidos() {
        Intent init = new Intent(this, Fallecidos.class);
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
                    fechaCasosActivos.setText(datos.getFecha() + "*");

                    /**Se obtienen los nombres y los casos activos confirmados de la respuesta del servicio web, se crean nuevos objetos de tipo Region,
                     * y estos son agregados a un nuevo Array que posteriormente será ordenado.*/
                    for (int i=0; i<datos.getReporte().length; i++) {
                        casosActivosArray.add(new Region(datos.getReporte()[i].getCasos_activos_confirmados(), datos.getReporte()[i].getRegion()));
                    }
                    sortingFunction(casosActivosArray);

                    /**Se crea el pie chart que mostrará las tres regiones con la mayor cantidad de casos activos de COVID-19 a la fecha.
                     * * NOTA: Como equipo nos vimos obligados a solo mostrar 3 regiones, ya que con una mayor cantidad de datos
                     * el gráfico no carga.*/
                    activosAnyChartView = findViewById(R.id.activos_any_chart_view);
                    pie = AnyChart.pie();

                    List<DataEntry> data = new ArrayList<>();
                    data.add(new ValueDataEntry(casosActivosArray.get(0).getNombre(), casosActivosArray.get(0).getId()));
                    data.add(new ValueDataEntry(casosActivosArray.get(1).getNombre(), casosActivosArray.get(1).getId()));
                    data.add(new ValueDataEntry(casosActivosArray.get(2).getNombre(), casosActivosArray.get(2).getId()));

                    pie.data(data);
                    pie.title("Las tres regiones con mayor cantidad de casos activos confirmados a la fecha (%)");
                    pie.labels().position("outside");

                    pie.legend().title().enabled(true);
                    pie.legend().title()
                            .text("Regiones")
                            .padding(0d, 0d, 10d, 0d);

                    pie.legend()
                            .position("center-bottom")
                            .itemsLayout(LegendLayout.HORIZONTAL)
                            .align(Align.CENTER);

                    activosAnyChartView.setChart(pie);

                    nombreCuartoLugar.setText("4. " + casosActivosArray.get(3).getNombre() + ":");
                    cantidadCuartoLugar.setText(String.valueOf(casosActivosArray.get(3).getId()) + " casos");
                    nombreQuintoLugar.setText("5. " + casosActivosArray.get(4).getNombre() + ":");
                    cantidadQuintoLugar.setText(String.valueOf(casosActivosArray.get(4).getId()) + " casos");
                    nombreSextoLugar.setText("6. " + casosActivosArray.get(5).getNombre() + ":");
                    cantidadSextoLugar.setText(String.valueOf(casosActivosArray.get(5).getId()) + " casos");
                    nombreSeptimoLugar.setText("7. " + casosActivosArray.get(6).getNombre() + ":");
                    cantidadSeptimoLugar.setText(String.valueOf(casosActivosArray.get(6).getId()) + " casos");
                    nombreOctavoLugar.setText("8. " + casosActivosArray.get(7).getNombre() + ":");
                    cantidadOctavoLugar.setText(String.valueOf(casosActivosArray.get(7).getId()) + " casos");
                    nombreNovenoLugar.setText("9. " + casosActivosArray.get(8).getNombre() + ":");
                    cantidadNovenoLugar.setText(String.valueOf(casosActivosArray.get(8).getId()) + " casos");
                    nombreDecimoLugar.setText("10. " + casosActivosArray.get(9).getNombre() + ":");
                    cantidadDecimoLugar.setText(String.valueOf(casosActivosArray.get(9).getId()) + " casos");
                    nombreOnceavoLugar.setText("11. " + casosActivosArray.get(10).getNombre() + ":");
                    cantidadOnceavoLugar.setText(String.valueOf(casosActivosArray.get(10).getId()) + " casos");
                    nombreDoceavoLugar.setText("12. " + casosActivosArray.get(11).getNombre() + ":");
                    cantidadDoceavoLugar.setText(String.valueOf(casosActivosArray.get(11).getId()) + " casos");
                    nombreTreceavoLugar.setText("13. " + casosActivosArray.get(12).getNombre() + ":");
                    cantidadTreceavoLugar.setText(String.valueOf(casosActivosArray.get(12).getId()) + " casos");
                    nombreCatorceavoLugar.setText("14. " + casosActivosArray.get(13).getNombre() + ":");
                    cantidadCatorceavoLugar.setText(String.valueOf(casosActivosArray.get(13).getId()) + " casos");
                    nombreQuinceavoLugar.setText("15. " + casosActivosArray.get(14).getNombre() + ":");
                    cantidadQuinceavoLugar.setText(String.valueOf(casosActivosArray.get(14).getId()) + " casos");
                    nombreDieciseisavoLugar.setText("16. " + casosActivosArray.get(15).getNombre() + ":");
                    cantidadDieciseisavoLugar.setText(String.valueOf(casosActivosArray.get(15).getId()) + " casos");
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
                Intent intent = new Intent(CasosActivosConfirmados.this, Home.class);
                startActivity(intent);
                finish();
                return false;
            case R.id.activity2:
                Intent intent2 = new Intent(CasosActivosConfirmados.this, ReporteDiario.class);
                startActivity(intent2);
                finish();
                return false;
            case R.id.activity3:
                Intent intent3 = new Intent(CasosActivosConfirmados.this, CasosAcumulados.class);
                startActivity(intent3);
                finish();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}