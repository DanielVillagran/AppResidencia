package com.firebaseloginapp.AccountActivity;

/**
 * Created by odvil on 28/11/2018.
 */

public class PojoMaestros {
    private String nombre;
    private String id;
    private String telefono;
    private String email;

    public PojoMaestros(String nombre, String id, String telefono, String email) {
        this.nombre = nombre;
        this.id = id;
        this.telefono = telefono;
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PojoMaestros() {
    }
}

