package com.example.covinfo_19.servicios_web;

import com.example.covinfo_19.servicios_web.respuestas.RespuestaWS;
import com.example.covinfo_19.servicios_web.respuestas.RegionesRWS;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServicioWeb {

    @POST("data/nacional")
    Call<RespuestaWS> getNationalData();

    @POST("regiones")
    Call<RegionesRWS> getAllRegions();

    @POST("data/{idregion}")
    Call<RespuestaWS> getDataForSpecifiedRegion(@Path("idregion") int idRegion);
}
