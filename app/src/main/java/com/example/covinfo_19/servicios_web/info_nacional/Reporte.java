package com.example.covinfo_19.servicios_web.info_nacional;

import java.util.Objects;

public class Reporte {
    private int acumulado_total;
    private int casos_nuevos_total;
    private int casos_nuevos_csintomas;
    private int casos_nuevos_ssintomas;
    private int casos_nuevos_snotificar;
    private int fallecidos;
    private int casos_activos_confirmados;

    public Reporte() { }

    public Reporte(int acumulado_total, int casos_nuevos_total, int casos_nuevos_csintomas, int casos_nuevos_ssintomas, int casos_nuevos_snotificar, int fallecidos, int casos_activos_confirmados) {
        this.acumulado_total = acumulado_total;
        this.casos_nuevos_total = casos_nuevos_total;
        this.casos_nuevos_csintomas = casos_nuevos_csintomas;
        this.casos_nuevos_ssintomas = casos_nuevos_ssintomas;
        this.casos_nuevos_snotificar = casos_nuevos_snotificar;
        this.fallecidos = fallecidos;
        this.casos_activos_confirmados = casos_activos_confirmados;
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
        Reporte reporte = (Reporte) o;
        return acumulado_total == reporte.acumulado_total &&
                casos_nuevos_total == reporte.casos_nuevos_total &&
                casos_nuevos_csintomas == reporte.casos_nuevos_csintomas &&
                casos_nuevos_ssintomas == reporte.casos_nuevos_ssintomas &&
                casos_nuevos_snotificar == reporte.casos_nuevos_snotificar &&
                fallecidos == reporte.fallecidos &&
                casos_activos_confirmados == reporte.casos_activos_confirmados;
    }

    @Override
    public int hashCode() {
        return Objects.hash(acumulado_total, casos_nuevos_total, casos_nuevos_csintomas, casos_nuevos_ssintomas, casos_nuevos_snotificar, fallecidos, casos_activos_confirmados);
    }

    @Override
    public String toString() {
        return "Reporte{" +
                "acumulado_total=" + acumulado_total +
                ", casos_nuevos_total=" + casos_nuevos_total +
                ", casos_nuevos_csintomas=" + casos_nuevos_csintomas +
                ", casos_nuevos_ssintomas=" + casos_nuevos_ssintomas +
                ", casos_nuevos_snotificar=" + casos_nuevos_snotificar +
                ", fallecidos=" + fallecidos +
                ", casos_activos_confirmados=" + casos_activos_confirmados +
                '}';
    }
}
