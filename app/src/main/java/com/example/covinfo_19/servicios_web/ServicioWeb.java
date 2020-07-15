package com.example.covinfo_19.servicios_web;

import com.example.covinfo_19.servicios_web.info_nacional.NacionalRSW;
import com.example.covinfo_19.servicios_web.info_por_region.RegionesRWS;

import retrofit2.Call;
import retrofit2.http.POST;

public interface ServicioWeb {

    @POST("data/nacional")
    Call<NacionalRSW> getNationalData();

    @POST("regiones")
    Call<RegionesRWS> getAllRegions();
}
