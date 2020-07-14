package com.example.covinfo_19.servicios_web;

import com.example.covinfo_19.servicios_web.respuestas.NacionalRSW;

import retrofit2.Call;
import retrofit2.http.POST;

public interface ServicioWeb {

    @POST("data/nacional")
    Call<NacionalRSW> getNationalData();
}
