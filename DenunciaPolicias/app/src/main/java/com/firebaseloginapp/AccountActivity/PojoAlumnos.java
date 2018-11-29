package com.firebaseloginapp.AccountActivity;

/**
 * Created by odvil on 21/01/2018.
 */

public class PojoAlumnos {
    private String id;
    private String nombre;
    private String ncontrol;
    private String maestro;
    private String status;
    private String semestre;
    private String documento;
    private String carrera;
    private String email;

    public PojoAlumnos() {
    }

    public PojoAlumnos(String id, String nombre, String ncontrol, String maestro, String email, String status, String semestre, String documento, String carrera) {
        this.id = id;
        this.nombre = nombre;
        this.ncontrol = ncontrol;
        this.maestro = maestro;
        this.status = status;
        this.semestre = semestre;
        this.documento = documento;
        this.carrera = carrera;
        this.email=email;
    }

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

    public String getNcontrol() {
        return ncontrol;
    }

    public void setNcontrol(String ncontrol) {
        this.ncontrol = ncontrol;
    }

    public String getMaestro() {
        return maestro;
    }

    public void setMaestro(String maestro) {
        this.maestro = maestro;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
}