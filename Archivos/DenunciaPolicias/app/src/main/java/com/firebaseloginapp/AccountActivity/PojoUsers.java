package com.firebaseloginapp.AccountActivity;

/**
 * Created by odvil on 21/02/2018.
 */

public class PojoUsers {
    String email;
    String id;
    String nombre;
    String telefono;

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

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public PojoUsers(){

    }

    public PojoUsers(String email, String id, String nombre, String telefono) {

        this.email = email;
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
    }
}
