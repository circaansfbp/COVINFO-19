package com.example.covinfo_19.servicios_web;

import java.util.Objects;

public class AllRegionReporte {
    private String region;
    private int acumulado_total;
    private int casos_nuevos_total;
    private int casos_nuevos_csintomas;
    private int casos_nuevos_ssintomas;
    private int casos_nuevos_snotificar;
    private int fallecidos;
    private int casos_activos_confirmados;

    public AllRegionReporte(String region, int acumulado_total, int casos_nuevos_total, int casos_nuevos_csintomas, int casos_nuevos_ssintomas, int casos_nuevos_snotificar, int fallecidos, int casos_activos_confirmados) {
        this.region = region;
        this.acumulado_total = acumulado_total;
        this.casos_nuevos_total = casos_nuevos_total;
        this.casos_nuevos_csintomas = casos_nuevos_csintomas;
        this.casos_nuevos_ssintomas = casos_nuevos_ssintomas;
        this.casos_nuevos_snotificar = casos_nuevos_snotificar;
        this.fallecidos = fallecidos;
        this.casos_activos_confirmados = casos_activos_confirmados;
    }

    public AllRegionReporte() { }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getAcumulado_total() {
        return acumulado_total;
    }

    public void setAcumulado_total(int acumulado_total) {
        this.acumulado_total = acumulado_total;
    }

    public int getCasos_nuevos_total() {
        return casos_nuevos_total;
    }

    public void setCasos_nuevos_total(int casos_nuevos_total) {
        this.casos_nuevos_total = casos_nuevos_total;
    }

    public int getCasos_nuevos_csintomas() {
        return casos_nuevos_csintomas;
    }

    public void setCasos_nuevos_csintomas(int casos_nuevos_csintomas) {
        this.casos_nuevos_csintomas = casos_nuevos_csintomas;
    }

    public int getCasos_nuevos_ssintomas() {
        return casos_nuevos_ssintomas;
    }

    public void setCasos_nuevos_ssintomas(int casos_nuevos_ssintomas) {
        this.casos_nuevos_ssintomas = casos_nuevos_ssintomas;
    }

    public int getCasos_nuevos_snotificar() {
        return casos_nuevos_snotificar;
    }

    public void setCasos_nuevos_snotificar(int casos_nuevos_snotificar) {
        this.casos_nuevos_snotificar = casos_nuevos_snotificar;
    }

    public int getFallecidos() {
        return fallecidos;
    }

    public void setFallecidos(int fallecidos) {
        this.fallecidos = fallecidos;
    }

    public int getCasos_activos_confirmados() {
        return casos_activos_confirmados;
    }

    public void setCasos_activos_confirmados(int casos_activos_confirmados) {
        this.casos_activos_confirmados = casos_activos_confirmados;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AllRegionReporte that = (AllRegionReporte) o;
        return acumulado_total == that.acumulado_total &&
                casos_nuevos_total == that.casos_nuevos_total &&
                casos_nuevos_csintomas == that.casos_nuevos_csintomas &&
                casos_nuevos_ssintomas == that.casos_nuevos_ssintomas &&
                casos_nuevos_snotificar == that.casos_nuevos_snotificar &&
                fallecidos == that.fallecidos &&
                casos_activos_confirmados == that.casos_activos_confirmados &&
                Objects.equals(region, that.region);
    }

    @Override
    public int hashCode() {
        return Objects.hash(region, acumulado_total, casos_nuevos_total, casos_nuevos_csintomas, casos_nuevos_ssintomas, casos_nuevos_snotificar, fallecidos, casos_activos_confirmados);
    }

    @Override
    public String toString() {
        return "AllRegionReporte{" +
                "region='" + region + '\'' +
                ", acumulado_total=" + acumulado_total +
                ", casos_nuevos_total=" + casos_nuevos_total +
                ", casos_nuevos_csintomas=" + casos_nuevos_csintomas +
                ", casos_nuevos_ssintomas=" + casos_nuevos_ssintomas +
                ", casos_nuevos_snotificar=" + casos_nuevos_snotificar +
                ", fallecidos=" + fallecidos +
                ", casos_activos_confirmados=" + casos_activos_confirmados +
                '}';
    }
}
