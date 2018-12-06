package com.firebaseloginapp.AccountActivity;

/**
 * Created by odvil on 21/01/2018.
 */

public class Pojo {
    private String id;
    private String desc;
    private String tipo;
    private String status;
    private String hora;
    private String fecha;
    private String lat;
    private String lon;
    private String imagen;


    public Pojo() {
    }

    public Pojo(String id, String desc, String tipo, String status, String hora, String fecha, String lat, String lon, String imagen) {
        this.id = id;
        this.desc = desc;
        this.tipo = tipo;
        this.status = status;
        this.hora = hora;
        this.fecha = fecha;
        this.lat = lat;
        this.lon = lon;
        this.imagen = imagen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
