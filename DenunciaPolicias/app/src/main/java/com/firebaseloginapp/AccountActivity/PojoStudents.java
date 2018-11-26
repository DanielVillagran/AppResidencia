package com.firebaseloginapp.AccountActivity;

/**
 * Created by odvil on 21/02/2018.
 */

public class PojoStudents {
    String email;
    String id;
    String nombre;
    String telefono;
    String maestro;
    String ncontrol;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setMaestro(String maestro) {
        this.maestro = maestro;
    }

    public String getMaestro() {
        return maestro;
    }

    public void setNcontrol(String ncontrol) {
        this.ncontrol = ncontrol;
    }

    public String getNcontrol() {
        return ncontrol;
    }


    public PojoStudents(){

    }

    public PojoStudents(String email, String id, String nombre, String telefono, String maestro, String ncontrol) {

        this.email = email;
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.maestro = maestro;
        this.ncontrol = ncontrol;
    }
}
