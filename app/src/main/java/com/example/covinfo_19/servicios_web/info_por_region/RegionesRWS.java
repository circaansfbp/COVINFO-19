package com.example.covinfo_19.servicios_web.info_por_region;

import java.util.Arrays;
import java.util.Objects;

public class RegionesRWS {
    private String info;
    private boolean estado;
    private Region[] regiones;

    public RegionesRWS(String info, boolean estado, Region[] regiones) {
        this.info = info;
        this.estado = estado;
        this.regiones = regiones;
    }

    public RegionesRWS() { }

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

    public Region[] getRegiones() {
        return regiones;
    }

    public void setRegiones(Region[] regiones) {
        this.regiones = regiones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegionesRWS that = (RegionesRWS) o;
        return estado == that.estado &&
                Objects.equals(info, that.info) &&
                Arrays.equals(regiones, that.regiones);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(info, estado);
        result = 31 * result + Arrays.hashCode(regiones);
        return result;
    }

    @Override
    public String toString() {
        return "RegionesRWS{" +
                "info='" + info + '\'' +
                ", estado=" + estado +
                ", regiones=" + Arrays.toString(regiones) +
                '}';
    }
}
