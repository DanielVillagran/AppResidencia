package com.firebaseloginapp.AccountActivity;

/**
 * Created by odvil on 21/01/2018.
 */

public class PojoRespuesta {
    private String id;
    private String desc;
    private String hora;
    private String fecha;
    private String imagen;


    public PojoRespuesta() {
    }

    public PojoRespuesta(String id, String desc, String hora, String fecha, String imagen) {
        this.id = id;
        this.desc = desc;
        this.hora = hora;
        this.fecha = fecha;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}