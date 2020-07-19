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
import com.example.covinfo_19.R;
import com.example.covinfo_19.ReporteDiario;
import com.example.covinfo_19.Home;
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

public class Fallecidos extends AppCompatActivity {
    private ServicioWeb servicio;

    private TextView fechaFallecidos;
    private Button volverCasosActivos;

    private TextView nombreCuartoLugar; private TextView cantidadCuartoLugar; private TextView nombreQuintoLugar;
    private TextView cantidadQuintoLugar; private TextView nombreSextoLugar; private TextView cantidadSextoLugar;
    private TextView nombreSeptimoLugar; private TextView cantidadSeptimoLugar; private TextView nombreOctavoLugar;
    private TextView cantidadOctavoLugar; private TextView nombreNovenoLugar; private TextView cantidadNovenoLugar;
    private TextView nombreDecimoLugar; private TextView cantidadDecimoLugar; private TextView nombreOnceavoLugar;
    private TextView cantidadOnceavoLugar; private TextView nombreDoceavoLugar; private TextView cantidadDoceavoLugar;
    private TextView nombreTreceavoLugar; private TextView cantidadTreceavoLugar; private TextView nombreCatorceavoLugar;
    private TextView cantidadCatorceavoLugar; private TextView nombreQuinceavoLugar; private TextView cantidadQuinceavoLugar;
    private TextView nombreDieciseisavoLugar; private TextView cantidadDieciseisavoLugar;

    private AnyChartView fallecidosAnyChartView;
    private Pie pie;

    private List<Region> fallecidosArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fallecidos);

        fechaFallecidos = findViewById(R.id.fecha_fallecidos);

        nombreCuartoLugar = findViewById(R.id.nombre_cuarto_lugar_fallecidos);
        cantidadCuartoLugar = findViewById(R.id.cantidad_cuarto_lugar_fallecidos);
        nombreQuintoLugar = findViewById(R.id.nombre_quinto_lugar_fallecidos);
        cantidadQuintoLugar = findViewById(R.id.cantidad_quinto_lugar_fallecidos);
        nombreSextoLugar = findViewById(R.id.nombre_sexto_lugar_fallecidos);
        cantidadSextoLugar = findViewById(R.id.cantidad_sexto_lugar_fallecidos);
        nombreSeptimoLugar = findViewById(R.id.nombre_septimo_lugar_fallecidos);
        cantidadSeptimoLugar = findViewById(R.id.cantidad_septimo_lugar_fallecidos);
        nombreOctavoLugar = findViewById(R.id.nombre_octavo_lugar_fallecidos);
        cantidadOctavoLugar = findViewById(R.id.cantidad_octavo_lugar_fallecidos);
        nombreNovenoLugar = findViewById(R.id.nombre_noveno_lugar_fallecidos);
        cantidadNovenoLugar = findViewById(R.id.cantidad_noveno_lugar_fallecidos);
        nombreDecimoLugar = findViewById(R.id.nombre_decimo_lugar_fallecidos);
        cantidadDecimoLugar = findViewById(R.id.cantidad_decimo_lugar_fallecidos);
        nombreOnceavoLugar = findViewById(R.id.nombre_onceavo_lugar_fallecidos);
        cantidadOnceavoLugar = findViewById(R.id.cantidad_onceavo_lugar_fallecidos);
        nombreDoceavoLugar = findViewById(R.id.nombre_doceavo_lugar_fallecidos);
        cantidadDoceavoLugar = findViewById(R.id.cantidad_doceavo_lugar_fallecidos);
        nombreTreceavoLugar = findViewById(R.id.nombre_treceavo_lugar_fallecidos);
        cantidadTreceavoLugar = findViewById(R.id.cantidad_treceavo_lugar_fallecidos);
        nombreCatorceavoLugar = findViewById(R.id.nombre_catorceavo_lugar_fallecidos);
        cantidadCatorceavoLugar = findViewById(R.id.cantidad_catorceavo_lugar_fallecidos);
        nombreQuinceavoLugar = findViewById(R.id.nombre_quinceavo_lugar_fallecidos);
        cantidadQuinceavoLugar = findViewById(R.id.cantidad_quinceavo_lugar_fallecidos);
        nombreDieciseisavoLugar = findViewById(R.id.nombre_dieciseisavo_lugar_fallecidos);
        cantidadDieciseisavoLugar = findViewById(R.id.cantidad_dieciseisavo_lugar_fallecidos);

        volverCasosActivos = findViewById(R.id.volver_casos_activos);
        volverCasosActivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToCasosActivos();
            }
        });

        /**Se llama a la librería para que se creen los objetos al hacer la llamada al servicio web.*/
        Retrofit retrofit = new Retrofit.Builder().baseUrl(" http://covid.unnamed-chile.com/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        /**Inyección de dependencia*/
        servicio = retrofit.create(ServicioWeb.class);

        getAllData();
    }

    /**Para regresar a la estadística anterior (Casos Activos)*/
    private void backToCasosActivos() {
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
                if (response != null && response.body() !=  null) {
                    AllRegionRWS datos = response.body();

                    /**Se setea la fecha del día.*/
                    fechaFallecidos.setText(datos.getFecha() + "*");

                    /**Se obtienen los nombres y los casos activos confirmados de la respuesta del servicio web, se crean nuevos objetos de tipo Region,
                     * y estos son agregados a un nuevo Array que posteriormente será ordenado.*/
                    for (int i=0; i<datos.getReporte().length; i++) {
                        fallecidosArray.add(new Region(datos.getReporte()[i].getFallecidos(), datos.getReporte()[i].getRegion()));
                    }
                    sortingFunction(fallecidosArray);

                    /**Se crea el pie chart que mostrará las tres regiones con la mayor cantidad de casos activos de COVID-19 a la fecha.
                     * * NOTA: Como equipo nos vimos obligados a solo mostrar 3 regiones, ya que con una mayor cantidad de datos
                     * el gráfico no carga.*/
                    fallecidosAnyChartView = findViewById(R.id.fallecidos_any_chart_view);
                    pie = AnyChart.pie();

                    List<DataEntry> data = new ArrayList<>();
                    data.add(new ValueDataEntry(fallecidosArray.get(0).getNombre(), fallecidosArray.get(0).getId()));
                    data.add(new ValueDataEntry(fallecidosArray.get(1).getNombre(), fallecidosArray.get(1).getId()));
                    data.add(new ValueDataEntry(fallecidosArray.get(2).getNombre(), fallecidosArray.get(2).getId()));

                    pie.data(data);
                    pie.title("Las tres regiones con mayor cantidad de fallecidos a la fecha (%)");
                    pie.labels().position("outside");

                    pie.legend().title().enabled(true);
                    pie.legend().title()
                            .text("Regiones")
                            .padding(0d, 0d, 10d, 0d);

                    pie.legend()
                            .position("center-bottom")
                            .itemsLayout(LegendLayout.HORIZONTAL)
                            .align(Align.CENTER);
                    fallecidosAnyChartView.setChart(pie);

                    nombreCuartoLugar.setText("4. " + fallecidosArray.get(3).getNombre() + ":");
                    cantidadCuartoLugar.setText(String.valueOf(fallecidosArray.get(3).getId()) + " fallecidos");
                    nombreQuintoLugar.setText("5. " + fallecidosArray.get(4).getNombre() + ":");
                    cantidadQuintoLugar.setText(String.valueOf(fallecidosArray.get(4).getId()) + " fallecidos");
                    nombreSextoLugar.setText("6. " + fallecidosArray.get(5).getNombre() + ":");
                    cantidadSextoLugar.setText(String.valueOf(fallecidosArray.get(5).getId()) + " fallecidos");
                    nombreSeptimoLugar.setText("7. " + fallecidosArray.get(6).getNombre() + ":");
                    cantidadSeptimoLugar.setText(String.valueOf(fallecidosArray.get(6).getId()) + " fallecidos");
                    nombreOctavoLugar.setText("8. " + fallecidosArray.get(7).getNombre() + ":");
                    cantidadOctavoLugar.setText(String.valueOf(fallecidosArray.get(7).getId()) + " fallecidos");
                    nombreNovenoLugar.setText("9. " + fallecidosArray.get(8).getNombre() + ":");
                    cantidadNovenoLugar.setText(String.valueOf(fallecidosArray.get(8).getId()) + " fallecidos");
                    nombreDecimoLugar.setText("10. " + fallecidosArray.get(9).getNombre() + ":");
                    cantidadDecimoLugar.setText(String.valueOf(fallecidosArray.get(9).getId()) + " fallecidos");
                    nombreOnceavoLugar.setText("11. " + fallecidosArray.get(10).getNombre() + ":");
                    cantidadOnceavoLugar.setText(String.valueOf(fallecidosArray.get(10).getId()) + " fallecidos");
                    nombreDoceavoLugar.setText("12. " + fallecidosArray.get(11).getNombre() + ":");
                    cantidadDoceavoLugar.setText(String.valueOf(fallecidosArray.get(11).getId()) + " fallecidos");
                    nombreTreceavoLugar.setText("13. " + fallecidosArray.get(12).getNombre() + ":");
                    cantidadTreceavoLugar.setText(String.valueOf(fallecidosArray.get(12).getId()) + " fallecidos");
                    nombreCatorceavoLugar.setText("14. " + fallecidosArray.get(13).getNombre() + ":");
                    cantidadCatorceavoLugar.setText(String.valueOf(fallecidosArray.get(13).getId()) + " fallecidos");
                    nombreQuinceavoLugar.setText("15. " + fallecidosArray.get(14).getNombre() + ":");
                    cantidadQuinceavoLugar.setText(String.valueOf(fallecidosArray.get(14).getId()) + " fallecidos");
                    nombreDieciseisavoLugar.setText("16. " + fallecidosArray.get(15).getNombre() + ":");
                    cantidadDieciseisavoLugar.setText(String.valueOf(fallecidosArray.get(15).getId()) + " fallecidos");
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
                Intent intent = new Intent(Fallecidos.this, Home.class);
                startActivity(intent);
                finish();
                return false;
            case R.id.activity2:
                Intent intent2 = new Intent(Fallecidos.this, ReporteDiario.class);
                startActivity(intent2);
                finish();
                return false;
            case R.id.activity3:
                Intent intent3 = new Intent(Fallecidos.this, CasosAcumulados.class);
                startActivity(intent3);
                finish();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}