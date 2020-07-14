package com.example.covinfo_19.servicios_web.respuestas;

import com.example.covinfo_19.servicios_web.Reporte;

import java.util.Objects;

public class NacionalRSW {
    private String info;
    private String fecha;
    private boolean estado;
    private Reporte reporte;

    public NacionalRSW() { }

    public NacionalRSW(String info, String fecha, boolean estado, Reporte reporte) {
        this.info = info;
        this.fecha = fecha;
        this.estado = estado;
        this.reporte = reporte;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Reporte getReporte() {
        return reporte;
    }

    public void setReporte(Reporte reporte) {
        this.reporte = reporte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NacionalRSW that = (NacionalRSW) o;
        return estado == that.estado &&
                Objects.equals(info, that.info) &&
                Objects.equals(fecha, that.fecha) &&
                Objects.equals(reporte, that.reporte);
    }

    @Override
    public int hashCode() {
        return Objects.hash(info, fecha, estado, reporte);
    }

    @Override
    public String toString() {
        return "NacionalRSW{" +
                "info='" + info + '\'' +
                ", fecha='" + fecha + '\'' +
                ", estado=" + estado +
                ", reporte=" + reporte +
                '}';
    }
}
