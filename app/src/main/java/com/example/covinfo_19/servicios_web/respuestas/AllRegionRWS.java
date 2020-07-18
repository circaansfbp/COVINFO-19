package com.example.covinfo_19.servicios_web.respuestas;

import com.example.covinfo_19.servicios_web.AllRegionReporte;

import java.util.Arrays;
import java.util.Objects;

public class AllRegionRWS {
    private String fecha;
    private String info;
    private boolean estado;
    private AllRegionReporte[] reporte;

    public AllRegionRWS(String fecha, String info, boolean estado, AllRegionReporte[] reporte) {
        this.fecha = fecha;
        this.info = info;
        this.estado = estado;
        this.reporte = reporte;
    }

    public AllRegionRWS() { }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public AllRegionReporte[] getReporte() {
        return reporte;
    }

    public void setReporte(AllRegionReporte[] reporte) {
        this.reporte = reporte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AllRegionRWS that = (AllRegionRWS) o;
        return estado == that.estado &&
                Objects.equals(fecha, that.fecha) &&
                Objects.equals(info, that.info) &&
                Arrays.equals(reporte, that.reporte);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(fecha, info, estado);
        result = 31 * result + Arrays.hashCode(reporte);
        return result;
    }

    @Override
    public String toString() {
        return "AllRegionRWS{" +
                "fecha='" + fecha + '\'' +
                ", info='" + info + '\'' +
                ", estado=" + estado +
                ", reporte=" + Arrays.toString(reporte) +
                '}';
    }
}
