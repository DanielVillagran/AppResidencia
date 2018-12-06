package com.firebaseloginapp.AccountActivity;

/**
 * Created by odvil on 29/11/2018.
 */

public class PojoDocumentos {
    private String ncontrol;
    private String id;
    private String email;

    public PojoDocumentos(String ncontrol, String id, String email, String documento) {
        this.ncontrol = ncontrol;
        this.id = id;
        this.email = email;
        this.documento = documento;
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

    public PojoDocumentos(String ncontrol, String id, String documento) {

        this.ncontrol = ncontrol;
        this.id = id;
        this.documento = documento;
    }

    private String documento;

    public PojoDocumentos(String ncontrol, String documento) {
        this.ncontrol = ncontrol;
        this.documento = documento;
    }

    public PojoDocumentos() {
    }

    public String getNcontrol() {
        return ncontrol;
    }

    public void setNcontrol(String ncontrol) {
        this.ncontrol = ncontrol;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }
}
